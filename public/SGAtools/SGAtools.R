####################################################################################
# SGATools v1.0
# Tools for image processing, normalizing and scoring Synthetic Genetic Array screens.
#
# This software is in the public domain, furnished "as is", without technical
# support, and with no warranty, express or implied, as to its usefulness for
# any purpose.
#
# Author:         Omar Wagih
# Licence:        Academic Free Licence v3.0
# Language:       English (CA)
# Last modified:  19/11/12
#
# Modification History 
# --------------------
# Dated  Version		Who		Description
# ----------------------------------------------------------
####################################################################################

# Load required libraries 
library(logging)    # For log file
library(stringr)    # For regex matching functions
library(bootstrap)  # For jackknife function

addHandler(writeToConsole)


SGATOOLS_VERSION = '1.0.0'
####################################################################################
# Reading section
readSGA <- function(file.paths, file.names=basename(file.paths), ad.paths=NA, replicates=4){
  
  # Length of vectors
  n1 = length(file.paths)
  n2 = length(file.names)
  
  loginfo('Reading input files')#
  
  sga.data.list = lapply(file.paths, function(file.path){
    # Index of the file path
    file.ind = which(file.paths == file.path)
    
    # Name of file 
    file.name = file.names[file.ind]
    
    loginfo('Reading input file (%d/%d) path = %s', file.ind, n1, file.path)
    
    # Read all lines first line, except @ and # symbols
    file.lines = readLines(file.path) 
    comment.meta = file.lines[grepl('@|#|[(]', file.lines)]
    file.lines = file.lines[!grepl('@|#|[(]', file.lines)]
    
    # If first line is Colony Project Data File, skip the first 13 lines 
    if(grepl('Colony Project Data File', file.lines[1], ignore.case=T)){
      loginfo('* Detected boone-lab format, skipping first 13 lines')
      file.lines = file.lines[-(1:13)]
    }
    
    # Read in the data: we only care about the first 3 columns
    sga.data = read.table(textConnection(file.lines), stringsAsFactors=F)[1:3]
    sga.data = sga.data[with(sga.data, order(V1, V2)), ]
    
    loginfo('* Done reading')
    
    # Find number of rows and columns
    num.rows = max(sga.data[[1]])
    num.cols = max(sga.data[[2]])
    
    loginfo('* Column classes = %s', sapply(sga.data, class))
    loginfo('* Number rows = %d', num.rows)
    loginfo('* Number cols = %d', num.cols)
    loginfo('* Data dimension = %s', dim(sga.data))
    
    file.name.metadata = fileNameMetadata(file.name)
    
    loginfo('* Obtaining file name metadata: valid = %s', file.name.metadata$is.valid)
    
    # Add plate id as file name
    sga.data[[4]] = file.name.metadata$filename
    
    rdbl = ceiling(sga.data[[1]]/sqrt(replicates))
    cdbl = ceiling(sga.data[[2]]/sqrt(replicates))
    
    # Default query - index of file
    sga.data[[5]] = as.character(file.ind)
    
    # Do we have valid meta data in the file name
    if(file.name.metadata$is.valid){
      # Update query
      sga.data[[5]] = file.name.metadata$query
    }
    
    # Default arrays as numbers - each replicate has a unique number 
    array.vals = ((cdbl - 1)* (num.rows/2)) + rdbl
    
    # Map to array definition files
    sga.data[,6:7] = mapArrayDefinition(file.name.metadata, array.vals, 
                                       rdbl, cdbl, ad.paths)
    
    # Set normalized colony size / score / KVP to NA 
    sga.data[,8:10] = NA
    
    # Add comment / meta data lines
    comment(sga.data) = comment.meta
    
    # Add other attributes
    attr(sga.data, 'file.name.metadata') = file.name.metadata
    attr(sga.data, 'num.rows') = num.rows
    attr(sga.data, 'num.cols') = num.cols
    
    # Set column names of data
    names(sga.data) = c('row', 'col', 'colonysize',
                        'plateid', 'query','array', 'array_annot',
                        'ncolonysize', 'score', 'kvp'  )
    sga.data
  })
  
  loginfo('Done reading all files')
  loginfo('-----------------------------------------------')
  return(sga.data.list)
}

mapArrayDefinition <- function(file.name.metadata, array.vals, rdbl, cdbl, ad.paths){
  # If we have array definition files - i.e they are not all NA
  if(! all(is.na(ad.paths))){
    loginfo('* Mapping array definition: number of array definition files = %d', length(ad.paths))
    
    # Get file names of paths
    ad.basenames = basename(ad.paths)
    
    ap.ids = str_extract(tolower(ad.paths), 'plate\\d+')
    if (is.na(ap.ids)) {
        loginfo('* Cherry picker plate name malformed... assume only one plate is available')
        ap.ids = c(file.name.metadata$arrayplateid)
    } else {
        ap.ids = as.numeric(str_extract(ap.ids, '\\d+'))
    }
    
    loginfo('* Mapping array definition: IDs of array plate files = %s', ap.ids)
    
    # Assume no valid array plate id, use the first one we have
    ind = 1
    
    if(file.name.metadata$is.valid){
      # If our file has a valid array plate id, match it to its array definition file path
      ind = which(file.name.metadata$arrayplateid == ap.ids)[1]
    }
    
    if(!is.na(ind)){
      # Read in corresponding array definition file - handle 5 lines?
      ad.data = read.table(ad.paths[ind], sep='\t', skip=5, header=F, stringsAsFactors=F)
      names(ad.data)[1:3] = c('c', 'r', 'Gene')
      
      m=data.frame(r=rdbl, c=cdbl)
      i = apply(m, 1, function(x){
        intersect(which(x[1] == ad.data$r), which(x[2] == ad.data$c))[1]
      })
      good.ind = !is.na(i)
      array.vals[good.ind] = ad.data$Gene[i][good.ind]
    }
    
  }else{
    loginfo('* Not mapping array definition: no array definition files')
  }
  
  ret = as.character(array.vals)
  
  orfs = unlist( lapply(strsplit(ret, '_'), function(i) i[1]) )
  annots = unlist( lapply(strsplit(ret, '_'), function(i) i[2]) )
  
  if (all(is.na(annots))) {
    annots = orfs
  }
  
  return(c(orfs, annots))
}

# Get metadata encoded in file name
# Format should be: username_query_arrayplateid...
# @param file.name: character file name
# @return ret: list of all meta data encoded in file name
fileNameMetadata <- function(file.name){
  split.pat =  '_|\\.'
  
  sp = strsplit(file.name,split.pat)[[1]]
  
  # Regular expression query of control screens
  ctrl.pat = 'wt|ctrl'
  # Regular expression for digit
  digit.pat = '^\\d+$'
  
  ret = NULL
  
  # Set file name - replace any spaces by a hyphen
  ret$filename = sub('\\s', '-', file.name)
  
  # Set user name - replace any spaces with a hyphen
  ret$username = sub('\\s', '-', sp[1])
  
  # Set query name
  ret$query = sub('\\s', '-', sp[3])
  
  # Set is.control
  ret$is.control = grepl(ctrl.pat, sp[2], ignore.case=T)
  
  # Set array plate id
  if(grepl(digit.pat, sp[4])){
    # Valid array plate id
    ret$arrayplateid = as.numeric(sp[4])
  }else{
    # Invalid array plate id
    ret$arrayplateid = NA
  }
  
  # Set is valid only if we have no NAs 
  ret$is.valid = !any(is.na(c(ret$query, ret$arrayplateid)))
  
  return(ret)
}

####################################################################################
# Normalization/scoring section

# Normalize a plate 
# @params replicates: replicates on the plates
# @params linkage.cutoff: linkage cutoff used ex. 200 = 200,000 KB
# @params overall.plate.median: value of overall plate median from large-scale experiments
# @params max.colony.size: computed from plate median, used to filter large colonies
# @return data frame: normalized data frame
normalizeSGA <- function(plate.data, 
                         replicates=4, 
                         linkage.cutoff=200,
                         keep.large=FALSE,
                         overall.plate.median=510, 
                         max.colony.size=1.5*overall.plate.median, 
                         intermediate.data=F,
                         linkage.file='',
                         linkage.genes=character(0)){
  
  loginfo('Normalizing plate: replicates = %d, overall.plate.median = %d, max.colony.size = %d', 
          replicates, overall.plate.median, max.colony.size)
  
  num.rows = attr(plate.data, 'num.rows')
  num.cols = attr(plate.data, 'num.cols')
 
  # Replciates regardless of array (unique spots) - not same as array name
  rdbl = ceiling(plate.data$row/sqrt(replicates))
  cdbl = ceiling(plate.data$col/sqrt(replicates))
  plate.data$spots = ((cdbl - 1)* (num.rows/2)) + rdbl
  
  # Ignored rows (none to begin with)
  ignore.ind = rep(FALSE, nrow(plate.data))
  names(ignore.ind) = NA
  
  ########## (F1) Linkage effect filter ##########
  linkage.ign = linkageFilter(plate.data, linkage.cutoff, linkage.file, linkage.genes)
  ignore.ind = mergeLogicalNames(linkage.ign, ignore.ind)
  
  ########## (N1) Plate normalization ##########
  plate.data$pnorm = plateNormalization(plate.data, 'colonysize', overall.plate.median)

  ########## (F2) Big replicates filter ##########
  if (!keep.large) {
    big.ign = bigReplicatesFilter(plate.data, 'pnorm', max.colony.size)
    ignore.ind = mergeLogicalNames(big.ign, ignore.ind)
  }
  
  ########## (N2) Spatial normalization ##########
  plate.data$snorm = spatialNormalization(plate.data, 'pnorm', ignore.ind)

  ########## (N3) Row column effect normalization ##########
  plate.data$rcnorm = rowcolNormalization(plate.data, 'snorm', ignore.ind)

  ########## (F3) Jackknife filter  ########## 
  # (ON SPOTS? TODO)
  jk.ign = jackknifeFilter(plate.data, 'rcnorm')
  ignore.ind = mergeLogicalNames(jk.ign, ignore.ind)
  
  ########## (N4) Plate normalization 2 ##########
  plate.data$pnorm2 = plateNormalization(plate.data, 'rcnorm', overall.plate.median)
  
  # Set normalized data to be the last normalized column
  plate.data$ncolonysize = plate.data$pnorm2
  
  # Cap normalized colony size at 1000
  field = 'ncolonysize'
  cp = plate.data[[field]] > 1000
  cp[is.na(cp)] = FALSE
  names(cp) = NA
  names(cp)[cp == T] = rep('CP', sum(cp)) 
  
  plate.data[[field]][ cp ] = 1000
  # Set NAs for ignored rows 
  ign = which(ignore.ind)
  plate.data[[field]][ ign ] = NA 
  
  # Add cp after we set NA
  ignore.ind = mergeLogicalNames(cp, ignore.ind)
  ign = which(ignore.ind)
  
  # Add status codes to KVPs
  status.kvp = sapply(names(ign), function(v){
    names(v) = 'status'
    kvpMapAsString(v)
  })
  plate.data$kvp[ign] = status.kvp
  
  # Make scale of normalized sizes 0-1
  plate.data$ncolonysize = plate.data$ncolonysize/overall.plate.median
  
  # Weird normalization effect, when normalized size is negative. 
  # TODO: check row/col normalization to fix this
  plate.data$ncolonysize[plate.data$ncolonysize < 0] = 0
  
  # LOL we're done, whew! return our data (minus extra cols generated)!
  if(!intermediate.data){
    m = match(c('spots','pnorm', 'snorm', 'rcnorm', 'pnorm2'), names(plate.data))
    plate.data.trunc = plate.data[,-m]
    attr(plate.data.trunc, 'file.name.metadata') = attr(plate.data, 'file.name.metadata')
  }
  
  return(plate.data.trunc)
}


# Score plates
# @params plate.data.list: list of data frames (plate data)
# @params scoring.function: 1 for Cij-CiCj, 2 for Cij/CiCj
# @return list: list of plate files, scored
scoreSGA <- function(plate.data.list, scoring.function=1){
  
  # Merge list info
  merged.dat = do.call('rbind', plate.data.list)
  
  metadata.table = lapply(plate.data.list, function(plate.data){
    d = attr(plate.data, 'file.name.metadata')
    as.data.frame(d)
  })
  metadata.table = do.call('rbind', metadata.table)[,4:6]
  
  # If we dont have any control/dm plates, we cant do scoring
  if(sum(metadata.table$is.control) < 1 | sum(!metadata.table$is.control) < 1)
    return(plate.data.list)
  
  # Do scoring for different array plate ids separatley 
  for(arrayplateid in unique(metadata.table$arrayplateid)){
    
    # Check if this array plate id has controls. if not, dont do anything
    is.ctrl = metadata.table$is.control & metadata.table$arrayplateid == arrayplateid
    is.dm = !metadata.table$is.control & metadata.table$arrayplateid == arrayplateid
    
    if(sum(is.ctrl) < 1 | sum(is.dm) < 1)
      next
    
    merged.dat.ctrl = do.call('rbind', plate.data.list[is.ctrl])
    merged.dat.dm = do.call('rbind', plate.data.list[is.dm])
    
    # We have at least one query, proceed to score
    # Get single mutant fitness of arrays (non control plates) - computed as median of the plate
    querys = unique(merged.dat.dm$query)
    query.smf = sapply(querys, function(curr.query){
      median( merged.dat.dm$ncolonysize[merged.dat.dm$query == curr.query] , na.rm=T)
    })
    
    # Get array smf from control plates
    arrays = unique(merged.dat.ctrl$array)
    array.smf = sapply(arrays, function(curr.array){
      median( merged.dat.ctrl$ncolonysize[ merged.dat.ctrl$array == curr.array ], na.rm=T )
    })
    
    # Use default overall median or median from plates?
    # Default overall median
    overall.median = 510
    # Get 60% middle median - R automatically removes NA values
    vals = sort(merged.dat$ncolonysize)
    length = length(vals)
    lower = 0.2
    upper = 0.8
    middle.median = median( vals[round(lower*length):round(upper*length)], na.rm = T)
    
    # Do the scoring
    plate.data.list[is.dm] = lapply(plate.data.list[is.dm], function(plate.data){
      # Single mutant fitnesses
      q.smf = query.smf[plate.data$query] / middle.median
      a.smf = array.smf[plate.data$array] / middle.median
      # Double mutant fitness
      dm = plate.data$ncolonysize / middle.median
      
      # Score accourding to scoring function
      if(scoring.function == 1){
        plate.data$score = dm - (q.smf * a.smf)
      }else if(scoring.function == 2){
        plate.data$score = dm / (q.smf * a.smf)
      }
      
      plate.data$ctrlncolonysize = plate.data.list[is.ctrl][[1]]$ncolonysize;
      
      plate.data
    })
    
  }# End array plate id loop
  
  # Done scoring, return the data
  return(plate.data.list)
}

# Merges names of logical vectors, collapsing using a comma
# @param a: logical vector 1
# @param b: logical vector 2
# @return logical: logical vector 1 and 2 merged
mergeLogicalNames <- function(a, b){
  ret = a | b
  sp1 = strsplit( names(a), ',')
  sp2 = strsplit( names(b), ',')
  
  new.nm = sapply(1:length(sp1), function(i){
    u = as.character(union(sp1[[i]], sp2[[i]]))
    u = u[!is.na(u) & !grepl('NA', u)]
    paste(u, collapse=',')
  })
  names(ret) = new.nm
  return(ret)
}

#  Key-value pair methods
# Convert character vector of kvps to a data frame
# @param kvps.string: character verctor of kvps  
# @return data frame: columns are keys, rows are different kvps
kvpsAsDataFrame <- function(kvps.string){
  # Get list of dataframes
  list.df = lapply(kvps.string, function(kvp.string){
    if(!is.na(kvp.string)){
      df = as.data.frame(kvpAsMap(kvp.string))
      t(df)
    }
  })
  # Remove NULL
  list.df = list.df[!sapply(list.df, is.null)]
  
  # If nothing produced we return an empty data frame
  if(length(list.df) == 0) return(as.data.frame(matrix(NA,0,0)))
  
  #Merge keys/value data frames
  merged.df = Reduce(function(...) merge(..., all=T), list.df)
  
  # Rename so everything is uppercase
  names(merged.df) = toupper(names(merged.df))
  return(merged.df)
}

# Convert key-value pair string to key-value map 
# @param kvp.string: key value pair as character 
# @return list: named vector
kvpAsMap <- function(kvp.string){
  # As a vector
  split = strsplit(kvp.string, '\\{|\\}|,\\s*')[[1]]
  split = split[split != ""]
  
  # Create the map
  df = as.data.frame(strsplit(split, '='), stringsAsFactors=F)
  map = as.character(df[2,])
  names(map) = df[1,]
  
  return(map)
}

# Convert key-value pair map to a character string 
# @param kvp.map: key value pair as map 
# @return list: character representation
kvpMapAsString <- function(kvp.map){
  kv = sapply(names(kvp.map), function(key){
    paste0(key, '=', kvp.map[[key]])
  })
  return(paste0('', paste0(kv, collapse=','), ''))
}

# Linkage filter: check if query and array are within close proximity on the same chromosome
# @param plate.data: SGA formatted data frame
# @param linkage.cutoff: in KB, If witin this value of eachother on same chromosome they will be ignored
# @return linkage.ignore: logical array with TRUE for rows to ignore. Status code as name
linkageFilter <- function(plate.data, linkage.cutoff=200, linkage.file='', linkage.genes=''){
  
  loginfo('# Applying linkage filter, linkage.cutoff = %d', linkage.cutoff)
  
  loginfo('Linkage file is at: %s', linkage.file)
  status.code = 'LK'
  
  # Load linkage files named: chromosome_coordinates.Rdata(R.data?)
  if(file.exists(linkage.file)){
    loginfo('Loading chromosome coordinates file')
    load('data/chrom_coordinates.Rdata')
  }else{
    loginfo('Chromosome coordinates file does not exist, returning empty data frame')
    chrom_coordinates = as.data.frame(matrix(NA, 0, 4))
  }
  
  if(linkage.cutoff < 0){
    loginfo('Skipping linkage correction...')
    linked = rep(FALSE, nrow(plate.data))
    names(linked) = NA
    return(linked)
  }
  
  
  mid.map = apply(chrom_coordinates[,3:4], 1, mean)
  names(mid.map) = chrom_coordinates[[1]]
  
  chr.map = chrom_coordinates[[2]]
  names(chr.map) = chrom_coordinates[[1]]
  
  linkage.genes = unique(c(plate.data$query, linkage.genes))
  loginfo('# Linkage genes including query = %s', paste0(linkage.genes, collapse=', '))
  linkage.genes = linkage.genes[ linkage.genes %in% chrom_coordinates[[1]] ]
  loginfo('# Linkage genes found in coords table = %s', paste0(linkage.genes, collapse=', '))
  
  # Get indicies for which row:query/array on same chromsome and within < cutoff
  #ind = plate.data$array %in% chrom_coordinates[[1]] 
  ar = plate.data$array
  
  linked = sapply(linkage.genes, function(g){
    (chr.map[g] == chr.map[ar]) & (abs( mid.map[g] - mid.map[ar] ) < (linkage.cutoff * 1e3))
  })
  
  linked = apply(linked, 1, any)
  linked[is.na(linked)] = FALSE
  
#   linked = sapply(plate.data$array, function(ar){
#     if(length(linkage.genes) == 0 | ! ar %in% chrom_coordinates[[1]] | linkage.genes[1] == ''){
#       FALSE
#     }else{
#       t = sapply(linkage.genes, function(g){
#         (chr.map[g] == chr.map[ar]) & (abs( mid.map[g] - mid.map[ar] ) < (linkage.cutoff * 1e3))
#       })
#       if(any(is.na(t) | is.null(t))){
#         FALSE
#       }else{
#         any(t)
#       }
#     }
#   })
  
  # Set status code
  names(linked) = NA
  ind = which(linked)
  names(linked)[ind] = rep(status.code, length(ind))
  
  loginfo('Linkage filter applied, total ignored = %d',sum(linked))
  return(linked)
}

# Plate normalization: brings all plates to one same scale
# @param plate.data: SGA formatted data frame
# @param field.to.normalize: name of the column in the data to normalize
# @return: vector of normalized values
plateNormalization <- function(plate.data, field.to.normalize, default.overall.median) {
  
  loginfo('# Normalizing for plate effect, default.overall.median = %d', default.overall.median)
  #Used to get median of center 60% of colonies (change if needed)
  lower = 0.2
  upper = 0.8
  

  # Get and sort our data to be plate normalized
  vals = sort(plate.data[[field.to.normalize]])
  vals.length = length(vals)
  
  # If we have insufficent data - return NAs
  if(length(vals) < 10){
    loginfo('Insufficient data for plate normalization, returning')
    return( rep(NA, nrow(plate.data)) )
  }
  
  # We have suffiecent data - get median of center 60% of colonies
  plate.median = median(vals[round(lower*vals.length) : round(upper*vals.length)], na.rm = TRUE)
  
  if (plate.median == 0) {
    loginfo('Median is 0, taking median of all')
    plate.median = mean(vals, na.rm = TRUE)
    loginfo(paste('New median is', plate.median))
  }
  
  # Store the final result computed using all data in result array
  normalized = plate.data[[field.to.normalize]] * (default.overall.median / plate.median)
  
  loginfo('Done plate normalization')
  # Return final result
  return(normalized)
}

# Spatial normalization: normalizes any gradient effect on the plate via median smoothing
# @param plate.data: SGA formatted data frame
# @param field.to.normalize: name of the column in the data to normalize
# @param ignore.ind: logical for any rows to be ignored 
# @return: vector of normalized values
spatialNormalization <- function(plate.data, field.to.normalize, ignore.ind) {
  
  loginfo('# Normalizing for spatial effect')
  
  num.rows = attr(plate.data, 'num.rows')
  num.cols = attr(plate.data, 'num.cols')
  
  # Get gaussian/average filters
  gaussian.filt = fgaussian(7,2)
  average.filt = faverage(9)
  
  # Data to be normalized before ignored
  before.ignore = plate.data[[field.to.normalize]]
  
  # Data to be normalized after ignored (used in the analysis)
  after.ignore = before.ignore
  after.ignore[ignore.ind] = NA
  
  # Construct plate matrix
  plate.mat = matrix(NA, num.rows, num.cols)
  rc.mat = as.matrix(plate.data[,1:2])
  plate.mat[rc.mat] = after.ignore
  
  # Fill NA with a placeholder (mean of all colonies) 
  t = plate.mat
  ind.na = which(is.na(t))
  t[ind.na] = mean(plate.mat, na.rm=TRUE)
  
  # Fill in NA with smoothed version of neighbors using gaussian blur
  filt.g = applyfilter(t, gaussian.filt)
  t[ind.na] = filt.g[ind.na]
  
  # Apply median/average filters
  filtered = medianfilter2d(t, 7, padding_type='replicate')
  filtered = applyfilter(filtered, average.filt, 'replicate')
  
  # Subtract the mean of the filtered data from the filtered data
  f = filtered / mean(filtered)
  
  # Subtract filtered - mean from  
  before.ignore = before.ignore / f[rc.mat]
  
  return(before.ignore)
}

# Row column effect normalization
# @param plate.data: SGA formatted data frame
# @param field.to.normalize: name of the column in the data to normalize
# @param ignore.ind: logical for any rows to be ignored 
# @return: vector of normalized values
rowcolNormalization <- function(plate.data, field.to.normalize, ignore.ind) {
  
  loginfo('# Normalizing for row column effect')
  # Data before rows ignored
  data.before.ignore = plate.data[[field.to.normalize]]
  
  # Ignore these rows
  data.after.ignore = data.before.ignore
  data.after.ignore[ignore.ind] = NA
  
  num.rows = attr(plate.data, 'num.rows')
  num.cols = attr(plate.data, 'num.cols')
  
  # Smooth across columns
  col.data = plate.data$col
  norm.col.effect = rowcolNormalizationHelper(col.data, data.after.ignore, num.rows, num.cols)
  
  # Smooth across rows
  norm.col.effect[ignore.ind] = NA
  row.data = plate.data$row
  norm.rowcol.effect = rowcolNormalizationHelper(row.data, norm.col.effect, num.rows, num.cols)
  
  return(norm.rowcol.effect)
}

# Helper for row/column effect normalization to avoid redundancy 
# @param rowcol.data: values of the row/col column in the plate data frame
# @param colony.size.data: values of the colony sizes being normalized 
# @return: vector of normalized values
rowcolNormalizationHelper <- function(rowcol.data, colony.size.data, num.rows, num.cols){
  
  ind.na = is.na(colony.size.data)
  
  # Sort values with index return
  vals.sorted = sort(rowcol.data[!ind.na], index.return=TRUE)
  ind.sorted = vals.sorted[[2]]
  vals.sorted = vals.sorted[[1]]
  
  # Window size to be used in lowess smoothing - currently not using it
  span = sum(vals.sorted <= 6) / (num.rows*num.cols)
  
  if(span>0 & length(span) > 0){
    lowess_smoothed = lowess(rowcol.data[!ind.na][ind.sorted], colony.size.data[!ind.na][ind.sorted], f=0.09, iter=5)
  }else{
    lowess_smoothed = list(y=colony.size.data[!ind.na][ind.sorted])
  }
  
#      pdf(sprintf('~/Desktop/lowess_%s_%s.pdf', max(rowcol.data, na.rm=T), i), width=18, height=18)
#      x = rowcol.data[!ind.na][ind.sorted]
#      y = colony.size.data[!ind.na][ind.sorted]
#      plot(x,y)
#      lines(lowess_smoothed, col='green')
#      lx = lowess_smoothed$x
  
  # We only care about Y values (colony size)
  lowess_smoothed = lowess_smoothed[['y']]
  
  tmp = lowess_smoothed / mean(lowess_smoothed)
  tmp[is.nan(tmp)] = 1
  colony.size.data[!ind.na][ind.sorted] = colony.size.data[!ind.na][ind.sorted] / tmp;
  colony.size.data[is.infinite(colony.size.data)] = 0
  
  #    lines(x=lx, tmp, col='red')
  #    points(x=lx, colony.size.data[!ind.na][ind.sorted] / tmp, col='blue', pch=3)
  #    dev.off()
  
  # Fill ignored NA values
  ind.uniq.rc = which(duplicated(rowcol.data))
  rc.to.smoothed = lowess_smoothed[ind.uniq.rc]
  names(rc.to.smoothed) = rowcol.data[ind.uniq.rc]
  
  ind.na = which(ind.na)
  
  na.smoothed = sapply(ind.na, function(i){
    i.rc = as.character(rowcol.data[i]) 
    
    i.smoothed = rc.to.smoothed[i.rc]
    
    i.smoothed / mean(lowess_smoothed)
  })
  na.smoothed = unlist(na.smoothed)
  #Update NAs
  colony.size.data[ind.na] = colony.size.data[ind.na] / as.vector(na.smoothed)
  
  return(colony.size.data)
}

# Jackknife filter (LOOCV): checks for colonies that contribute more than 90% of total variance in their replicates
# @param plate.data: SGA formatted data frame
# @param field.to.filter: name of the column in the data to filter
# @return jk.ignore.logical: logical array with TRUE for rows to ignore. Status code as name
jackknifeFilter <- function(plate.data, field.to.filter){
  
  loginfo('# Applying jackknife filter')
  
  # Status code of filter
  status.code = 'JK'
  
  # Get all unique queries
  uniq.query = unique(plate.data$query)
  
  # Get all unique arrays
  uniq.array = unique(plate.data$array)
  uniq.spots = unique(plate.data$spots)
  
  # Remove HIS3 from arrays
  uniq.array = uniq.array[ ! grepl('YOR202W', uniq.array, ignore.case=T) ]
  uniq.spots = uniq.spots[ ! grepl('YOR202W', uniq.array, ignore.case=T) ]
  
  # Function used for jackknife function (sd, ignoring NA)
  theta <- function(x){sd(x, na.rm=T)}
  
  jk.ignore = lapply(uniq.spots, function(curr.spot){
    
    # Ignore HIS3 from arrays
    # if( grepl('YOR202W', curr.array, ignore.case=T) ) NULL
    
    curr.ind = which(plate.data$spot == curr.spot)
    # Get indices of our current array
    vals = plate.data[[field.to.filter]][curr.ind]
    # Get NA values
    ind.na = is.na(vals)
    
    # If we dont have enough non-NA values
    if( sum(!is.na(vals))  < 2 ) NULL
    
    # Get jackknife variances 
    jk.vals = jackknife(vals, theta)$jack.values
    
    # Get total variance and jackknife variances
    total.var = var(vals, na.rm=T) * (length(vals)-1)
    jk.var =  (jk.vals^2) * (length(jk.vals)-2)
    
    # Find colonies that contribute more than 90% of total variance
    t = which( (total.var - jk.var) >  (0.9*total.var) )
    curr.ind[t]
  })
  
  # These are the indicies to be ignored
  jk.ignore.ind = unlist(jk.ignore[! sapply(jk.ignore, is.null)])
  
  # Turn into logical values i.e. if we had indicies 1, 3 to be ignored, 
  # it will be converted to TRUE FALSE TRUE FALSE FALSE ......
  jk.ignore.logical = 1:nrow(plate.data) %in% jk.ignore.ind
  
  # Add status code JK - jackknife failed
  names(jk.ignore.logical) = NA
  names(jk.ignore.logical)[jk.ignore.ind] = rep(status.code, length(jk.ignore.ind))
  
  loginfo('Done jackknife filter, total ignored = %d', sum(jk.ignore.logical))
  return(jk.ignore.logical)
}

# Big replicates filter: remove excessively large colonies replicates
# @param plate.data: SGA formatted data frame
# @param field.to.filter: name of the column in the data to filter
# @param max.colony.size: "large" colony threshhold, usually 1.5 * median of plate
# @return big.logical: logical array with TRUE for rows to ignore. Status code as name
bigReplicatesFilter <- function(plate.data, field.to.filter, max.colony.size, ignore.ind){
  
  loginfo('# Applying big replicates filter, max.colony.size = %d', max.colony.size)
  # Status code of filter
  status.code = 'BG'
  
  #Find indicies of large colonies
  large.ind = which(plate.data[[field.to.filter]] >= max.colony.size)
  
  # Gets spots of large colonies, and returns the count of each spot
  big.spots = table(plate.data$spots[large.ind])
  
  # Get colonies such that their spot contains at least 3 big colonies
  big.spots = big.spots[big.spots >= 3]
  spots.to.remove = names(big.spots)
 
  # Get which colonies are in spots to remove
  big.ind = which(plate.data$spots %in% spots.to.remove)
  
  big.logical = 1:nrow(plate.data) %in% big.ind
  
  # Set status code BG BIG REP?
  names(big.logical) = NA
  names(big.logical)[big.ind] =  rep(status.code, length(big.ind))
  
  loginfo('Done big replicates filter, total ignored = %d', sum(big.logical))
  return(big.logical)
}


####################################################################################
# Filter functions used in spaital normalization: rewritten from matlab for R 

#Returns a gaussian filter matrix with dimensions x by x: equal to fspecial function in matlab
#Inputs:
#  x = dimensions (number of rows/cols) of the returned gaussian filter
#	sigma = standard deviation 
fgaussian <- function(x, sigma){
  x = seq(x)
  mat = matrix(NA, length(x),length(x));
  for(i in 1:length(x)){
    for(j in 1:length(x)){
      n1 = x[i];
      n2 = x[j];
      mat[i,j] = exp(-(n1^2+n2^2)/(2*sigma^2));
    }
  }
  mat = mat/sum(mat)
  return(mat)
}

#Average filter
faverage <- function(size){
  x = 1/(size*size)
  ret = matrix(rep(x, size*size), size,size)
  return(ret)
}

#Helper function for fgaussian - given some value x, returns a gradient array begining with 0 on the inside and increasing outwards. Example: x = 7 returns [3,2,1,0,1,2,3] 
#Inputs:
#	x = number of elements in the returned array
seq <- function(x){
  n = x;
  x = c(1:x)
  if(n%%2){
    rhs = x[1:floor(length(x)/2)];
    lhs = rev(rhs);
    return(c(lhs,0,rhs))
  }else{
    rhs = x[1:floor(length(x)/2)] - 0.5;
    lhs = rev(rhs);
    return(c(lhs,rhs))
  }
}

#Applies a filter to a matrix: see imfilter (matlab) with replicate option
#Inputs:
#	mat = matrix to which the filter is applied
# 	filter = a square matrix filter to be applied to the matrix 
applyfilter <- function(mat, filter, padding_type = 'zeros'){
  mat2 = mat
  fs = dim(filter);
  if(fs[1] != fs[2])
    stop('Filter must be a square matrix')
  if(fs[1] %% 2 == 0)
    stop('Filter dimensions must be odd')
  if(fs[1] == 1)
    stop('Filter dimensions must be greater than one')
  
  x = fs[1];
  a = (x-1)/2;
  
  s = dim(mat2)
  r = matrix(0, s[1], s[2])
  
  start = 1+a;
  end_1 = s[1]+a;
  end_2 = s[2]+a;
  
  mat2 = padmatrix(mat, a, padding_type)
  
  for(i in start:end_1){
    for(j in start:end_2){
      temp = mat2[(i-a):(i+a), (j-a):(j+a)] * filter;
      r[(i-a),(j-a)] = sum(temp)
    }
  }
  return(r)
}

#Applies a filter to a matrix: see imfilter (matlab) with replicate option
#Inputs:
#	mat = matrix to which the filter is applied
# 	dim = number of rows/cols of window
medianfilter2d <- function(mat, dim, padding_type = 'zeros'){
  mat2 = mat
  fs = c()
  fs[1] = dim
  fs[2] = dim
  
  if(fs[1] != fs[2])
    stop('Filter must be a square matrix')
  if(fs[1] %% 2 == 0)
    stop('Filter dimensions must be odd')
  if(fs[1] == 1)
    stop('Filter dimensions must be greater than one')
  
  x = fs[1];
  a = (x-1)/2;
  
  s = dim(mat2)
  r = matrix(0, s[1], s[2])
  
  start = 1+a;
  end_1 = s[1]+a;
  end_2 = s[2]+a;
  
  mat2 = padmatrix(mat, a, padding_type)
  
  for(i in start:end_1){
    for(j in start:end_2){
      temp = mat2[(i-a):(i+a), (j-a):(j+a)];
      r[(i-a),(j-a)] = median(temp)
    }
  }
  return(r)
}

#Adds a padding to some matrix mat such that the padding is equal to the value of the nearest cell
#Inputs:
#	mat = matrix to which the padding is added
#	lvl = number of levels (rows/columns) of padding to be added
#	padding = type of padding on the matrix, zero will put zeros as borders, replicate will put the value of the nearest cell
padmatrix <- function(mat, lvl, padding){
  s = dim(mat);
  row_up = mat[1,]
  row_down = mat[s[1],]
  
  if(padding == 'zeros'){
    row_up = rep(0, length(row_up))
    row_down = rep(0, length(row_down))
  }
  #Add upper replicates
  ret = t(matrix(rep(row_up, lvl), length(as.vector(row_up))))
  #Add matrix itself
  ret = rbind(ret, mat)
  #Add lower replicates
  ret = rbind(ret, t(matrix(rep(row_down, lvl), length(as.vector(row_down)))))
  
  #Add columns
  s = dim(ret);
  col_left = ret[,1]
  col_right = ret[,s[2]]
  
  if(padding == 'zeros'){
    col_left = rep(0, length(col_left))
    col_right = rep(0, length(col_right))
  }
  
  #Add left columns
  ret2 = matrix(rep(col_left, lvl), length(as.vector(col_left)))
  #Add matrix itself
  ret2 = cbind(ret2, ret)
  #Add right columns
  ret2 = cbind(ret2, matrix(rep(col_right, lvl), length(as.vector(col_right))))
  
  #return 
  return(ret2)
}

