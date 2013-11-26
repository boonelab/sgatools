suppressPackageStartupMessages(library(optparse))
suppressPackageStartupMessages(library(logging))
suppressPackageStartupMessages(library(plyr))
suppressPackageStartupMessages(library(xlsx))
options(warn=-1) #suppress warnings

wb <- createWorkbook()
option_list <- list(
    make_option(c("-i", "--inputfiles"), help="Input plate files (required): containing colony sizes to be normalized (comma separated for multiple files or a zip file with all input files)" ),
    make_option(c("-a", "--adfiles"), help="Array definition file paths (optional): files containing array layout of plate (comma separated for multiple files)", default=NA, type="character"),
    make_option(c("-y", "--adname"), help="Array definition summary", default=NA, type="character"),
    make_option(c("-o", "--outputdir"), help="Output directory (optional): where the normalized/scored results are saved [default %default]", default=getwd(), type="character"),
    make_option(c("-n", "--savenames"), help="Save names (optional): for input files if any different (comma separated, in same order as inputfiles)", default=NA, type="character"),
    make_option(c("-l", "--linkagecutoff"), help="Linkage cutoff (optional): specified in KB, if -1 no linkage correction is applied [default %default]", default=-1, type="integer"),
    make_option(c("-g", "--linkagegenes"), help="Linkage genes (optional): arrays within the linkage cutoff of these genes are removed [default %default]", default="", type="character"),
    make_option(c("-r", "--replicates"), help="Replicates (optional): value indicating number of replicates in the screen  [default %default]", default=4, type="integer"),
    make_option(c("-s", "--score"), help="Score normalized results(optional): include to score normalized results [default %default]", action="store_true", default=FALSE),
    make_option(c("-f", "--sfunction"), help="Score function used(optional): '1' for subtraction, '2' for dividing [default %default]", type="integer", default=1),
    make_option(c("-d", "--nignore"), help="Ignore normalizations/filters (optional): comma separated normalization/filter codes to be ignored.  [default %default]", default="", type="character"),
    make_option(c("-w", "--wd"), help="Working directory (optional): set R script working directory.  [default %default]", default="", type="character"),
    make_option(c("-L", "--keeplarge"), help="Working directory (optional): set R script working directory.  [default %default]", action="store_true", default=FALSE)
)

# Parse the agruments
args=parse_args(OptionParser(option_list = option_list))

# Set working directory
if(args$wd != ""){
  setwd(args$wd)
}

sheet <- createSheet(wb, sheetName="README")
readMeLines = readLines('README_NS.txt')
addDataFrame(readMeLines, sheet, startRow=1, startColumn=1, row.names=F, col.names=F)

linkage.file = file.path(getwd(), "data", "chrom_coordinates.Rdata")
genemap.file = file.path(getwd(), "data", "genemap.Rdata")

source('SGAtools.R')

# Load rdata Object: genemap
load(genemap.file)
if(args$linkagecutoff == -1){
  args$linkagegenes = character(0)
  linkage.genes = character(0)
}else{
  linkage.genes = unlist(strsplit(args$linkagegenes, ','))
  z = names(genemap)
  names(z) = genemap
  ind = linkage.genes %in% names(z)
  linkage.genes[ind] = z[linkage.genes[ind]]
  linkage.genes = toupper(linkage.genes)
}
# Split by colon (illegal character for filenames)
args$inputfiles = unlist(strsplit(args$inputfiles, ':'))
args$savenames = unlist(strsplit(args$savenames, ':'))
args$adfiles = unlist(strsplit(args$adfiles, ':'))

# Validate existance of output directories
if(length(list.dirs(args$outputdir)) == 0){
  stop(paste(args$outputdir, 'is not valid a output directory'))
}

# Validate existance of input files / array def files
files = c(args$inputfiles, args$adfiles)
files = files[!is.na(files)]
valid.file = file.exists(files)
if( sum(!valid.file) > 0 ){
  invalid.file = files[!valid.file][1]
  stop(paste(invalid.file, 'is not valid a file path'))
}

# Ensure linkage cutoff/replicates value is valid
if( is.na(args$linkagecutoff) ){
  stop('Invalid linkage cutoff value')
}
if(! args$replicates %in% c(1,4)  ){
  stop('Invalid replicates value')
}

ign.codes = c('PL1', 'SPA', 'RCE', 'CMP', 'PL2', 'JKF', 'LKF', 'BRF', 'CPF')

# Check  if we have ignore codes, if we do validate them
if(args$nignore != ""){
  # Split by comma and validate
  args$nignore = toupper(strsplit(args$nignore, ',')[[1]])
  invalid.code = ! args$nignore %in% ign.codes
  
  # If at least one code is invalid
  if(sum(invalid.code) > 0){
    stop(paste(args$nignore[invalid.code][1], 'is not a valid normalization/filter code'))
  }
}

# Check that savenames are same lenth as input files
if(is.na(args$savenames[1])){
  args$savenames = basename(args$inputfiles)
}
if( length(args$savenames) != length(args$inputfiles)  ){
  stop('Number of save names must be equal to the number of input files')
}

if(! args$sfunction %in% c(1,2) & !is.na(args$sfunction)){
  stop(paste0(args$sfunction, ' is an inavlid scoring function, must be 1 or 2'))
}

# Make sort to make CTRL data come last
z = grepl('_ctrl|_wt',args$savenames)
if(sum(z) > 0){
  args$inputfiles = c(args$inputfiles[!z],args$inputfiles[z]) 
  args$savenames = c(args$savenames[!z],args$savenames[z]) 
}

# Run normalization/scoring
sgadata.r = readSGA(args$inputfiles, args$savenames, args$adfiles, args$replicates)
sgadata.ns = lapply(sgadata.r, normalizeSGA, replicates=args$replicates, 
    linkage.cutoff=args$linkagecutoff, linkage.file=linkage.file, linkage.genes=linkage.genes,
    keep.large=args$keeplarge)

metadata.table = lapply(sgadata.ns, function(plate.data){
      d = attr(plate.data, 'file.name.metadata')
      as.data.frame(d)
    })
mt = do.call('rbind', metadata.table)[,4:6]
mt = mt[mt$is.valid,]
ctrl_apid = mt[mt[[1]] == TRUE,'arrayplateid']
dm_apid = mt[mt[[1]] == FALSE,'arrayplateid']
do.combined = length(intersect(dm_apid,ctrl_apid)) > 0

# Score data if requested
if(args$score){
  sgadata.ns = scoreSGA(sgadata.ns, scoring.function=args$sfunction)
}

# Set working directory to output directory
setwd(args$outputdir)

# Do header comments
lk = '# Linkage correction applied: '
if(args$linkagecutoff > 0){
  lk = paste0(lk, 'Yes', '(', args$linkagecutoff,'KB), linkage genes:', args$linkagegenes)
}else{
  lk = paste0(lk, 'No')
}

ad = '# Plate layout applied: '
if(all(is.na(args$adfiles))){
  ad = paste0(ad, 'No')
}else{
  ad = paste0(ad, args$adname)
}

rep = paste0('# Replicates: ', args$replicates)

comment.ns = paste('# Normalized by SGAtools',SGATOOLS_VERSION,'on', Sys.time())
if(args$score){
  comment.ns = paste('# Normalized and scored by SGAtools',SGATOOLS_VERSION,'on', Sys.time())
  sf = 'Cij - CiCj'
  if(args$sfunction == 2) sf = 'Cij / CiCj'
  comment.ns = c(comment.ns, paste0('# Scoring function: ', sf)) 
}
col.header = '(1)Row\t(2)Column\t(3)Raw colony size\t(4)Plate id / file name\t(5)Query\t(6)Array\t(7)Normalized colony size\t(8)Score\t(9)Additional information'
comment.ns = c(comment.ns, ad, rep, lk, col.header)

tt <- function(x){
  x=x[!is.na(x)]
  if(length(x) <2 | all(x == x[1])){
    return(NA)
  }else{
    return( t.test(x)$p.value )
  }
}

combined = lapply(sgadata.ns, function(plate.data){
      df = plate.data
      
      d = attr(df, 'file.name.metadata')
      if (d$is.control & do.combined) {
        return()
      }
      
      collapsed = ddply(df, c("query", "array_annot"), function(df) { 
            c( mean(df$colonysize, na.rm=T) , 
                paste0(unique(df$plateid), collapse=';') , 
                paste0(unique(df$query), collapse=';') , 
                paste0(unique(df$array), collapse=';') , 
                paste0(unique(df$array_annot), collapse=';') , 
                mean(df$ncolonysize, na.rm=T), 
                mean(df$score, na.rm=T),
                sd(df$score, na.rm=T),
                paste0(unique(df$kvp[!is.na(df$kvp)], na.rm=T), collapse=';'),
                sd(df$ncolonysize, na.rm=T),
                tt(df$ncolonysize),
                mean(df$ctrlncolonysize, na.rm=T), 
                sd(df$ctrlncolonysize, na.rm=T)
            ) 
          })
      collapsed[,1:2] = NA
      names(collapsed) = c('row', 'col', 'colonysize', 'plateid', 
          'query', 'array', 'array_annot', 'ncolonysize', 'score', 'scoreSd', 'kvp', 'sd', 'pvalue', 'ctrlncolonysize', 'ctrlsd')
      
      collapsed$kvp[collapsed$kvp == ""] = NA
      collapsed$ncolonysize[collapsed$ncolonysize == "NaN"] = NA
      collapsed$score[collapsed$score == "NaN"] = NA
      collapsed$colonysize = round(as.numeric(collapsed$colonysize), 2)
      collapsed$ncolonysize = round(as.numeric(collapsed$ncolonysize), 5)
      collapsed$ctrlncolonysize = round(as.numeric(collapsed$ctrlncolonysize), 5)
      collapsed$score = round(as.numeric(collapsed$score), 5)
      
      collapsed
    })

combined = do.call(rbind, combined)

ind = !is.na(combined$sd)
combined$sd = round(as.numeric(combined$sd), digits=3)
combined$scoreSd = round(as.numeric(combined$scoreSd), digits=3)
combined$pvalue = round(as.numeric(combined$pvalue), digits=5)
combined$ctrlsd = round(as.numeric(combined$ctrlsd), digits=3)

combined.dat = combined[ , c('row','col', 'colonysize', 'plateid', 'query', 'array', 'ncolonysize', 'score', 'kvp')]

# Reindex the columns
combined.new = combined[ , c('query','array', 'array_annot', 'ncolonysize', 'sd', 'ctrlncolonysize', 'ctrlsd', 'score', 'scoreSd', 'pvalue', 'kvp')]
ind = combined.new$query %in% names(genemap)
combined.new$queryName <- combined.new$query
combined.new$queryName[ind] = genemap[combined.new$queryName[ind]]

ind = combined.new$array %in% names(genemap)
combined.new$arrayName <- combined.new$array
combined.new$arrayName[ind] = genemap[combined.new$arrayName[ind]]
combined.new = combined.new[ , c('query', 'queryName','array', 'arrayName', 'array_annot', 'ncolonysize', 'sd', 'ctrlncolonysize', 'ctrlsd', 'score', 'scoreSd', 'pvalue', 'kvp')]

# Combined data
savename =  "combined_data.dat"
comments = c('# Combined data file', comment.ns)
comments[length(comments)] = '# (1)Row\t(2)Column\t(3)Raw colony size\t(4)Plate id / file name\t(5)Query\t(6)Array\t(7)Normalized colony size\t(8)Normalized colony std\t(9)Score\t(10)p-Value\t(11)Additional information'
writeLines(comments, savename)
write.table(combined.dat, savename, quote=F, row.names=F, col.names=F, sep="\t", append=T)

sheet <- createSheet(wb, sheetName="Combined data")
addDataFrame(list("Query ORF", "Query Name", "Array ORF", "Array Name", "Array annotation", "Normalized colony size (EXPERIMENT)",  "Normalized colony std. dev. (EXPERIMENT)", "Normalized colony size (CONTROL)", "Normalized colony std. dev. (CONTROL)", "Score", "Score stdev", "p-Value", "Additional information"), sheet, startRow=1, startColumn=1, row.names=F, col.names=F)
addDataFrame(combined.new, sheet, startRow=2, startColumn=1, row.names=F, col.names=F, showNA=T)

# Write generated files
for(i in 1:length(sgadata.ns)){
  savename = args$savename[i]
  
  comments = comment(sgadata.r[[i]])
  comments = c(comments, comment.ns)
  # Write comments
  writeLines(comments, savename)
  
  # Write plate data
  plate.data = sgadata.ns[[i]]
  plate.data$ncolonysize = round(plate.data$ncolonysize,5)
  plate.data$score = round(plate.data$score,5)
  plate.data = plate.data[,!(names(plate.data) %in% c('ctrlncolonysize'))]
  
  sheetName = savename
  
  if (nchar(sheetName) > 31) {
    sheetName = substr(sheetName, 0, 25)
    sheetName = paste0(sheetName, " (", i, ")")
  }
  
  sheet <- createSheet(wb, sheetName=sheetName)
  addDataFrame(list("Row", "Column", "Raw colony size", "Plate id / file name", "Query", "Array", "Normalized colony size", "Score", "Additional information"), sheet, startRow=1, startColumn=1, row.names=F, col.names=F)
  addDataFrame(plate.data, sheet, startRow=2, startColumn=1, row.names=F, col.names=F, showNA=T)
  
  plate.data$kvp <- sapply(plate.data$kvp, FUN=paste, collapse="-")
  write.table(plate.data, savename, quote=F, row.names=F, col.names=F, sep="\t", append=T)
}

if(args$score){
  # Scores only file
  savename =  "scores.dat"
  comments = c('# Scores only file', comment.ns)
  comments[length(comments)] = '# (1)Query ORF\t(2)Array ORF\t(4)Array annotation\t(3)Score\t(4)Standard deviation\t(5)P-value'
  writeLines(comments, savename)
  sc = combined[,c('query','array', 'array_annot', 'score', 'sd', 'pvalue')]
  sc = sc[!is.na(sc[[3]]),]
  
  # Map orf to gene name
  ind = sc[[1]] %in% names(genemap)
  sc[[1]][ind] = genemap[sc[[1]][ind]]
  ind = sc[[2]] %in% names(genemap)
  sc[[2]][ind] = genemap[sc[[2]][ind]]
  
  # Write
  write.table(sc, savename, quote=F, row.names=F, col.names=F, sep="\t", append=T)
}

# README
writeLines(readMeLines, 'README.txt')

############################################################
# Another sheet with averaged colony sizes, scores, etc... #
############################################################

combined = lapply(sgadata.ns, function(plate.data){
      df = plate.data
      
      collapsed = ddply(df, c("query", "array", "array_annot"), function(df) { 
            c(  paste0(unique(df$plateid), collapse=';') , 
                mean(df$colonysize, na.rm=T) ,
                sd  (df$colonysize, na.rm=T),
                mean(df$ncolonysize, na.rm=T),
                sd  (df$ncolonysize, na.rm=T),
                mean(df$score, na.rm=T),
                sd  (df$score, na.rm=T),
                tt  (df$ncolonysize),
                paste0(unique(df$kvp[!is.na(df$kvp)], na.rm=T), collapse=';')
            ) 
          })
      names(collapsed) = c('query', 'array', 'array_annot', 'plateid', 'colonysize','colonysizeSd', 
          'ncolonysize', 'ncolonysizeSd', 'score', 'scoreSd', 'pvalue', 'kvp')
      
      collapsed$kvp[collapsed$kvp == ""] = NA
      collapsed$ncolonysize[collapsed$ncolonysize == "NaN"] = NA
      collapsed$score[collapsed$score == "NaN"] = NA
      collapsed$colonysize = round(as.numeric(collapsed$colonysize), 2)
      collapsed$ncolonysize = round(as.numeric(collapsed$ncolonysize), 5)
      collapsed$score = round(as.numeric(collapsed$score), 5)
      
      collapsed
    })

combined = do.call(rbind, combined)

sheet <- createSheet(wb, sheetName='Summary')
addDataFrame(list("Query", "Array", "Array annotation", "Plate id / file name", "Raw avg. colony size", "Raw avg. colony size std. dev.", "Normalized colony size", "Normalized colony size std. dev.", "Score", "Score std. dev.", "p-Value", "Additional information"), sheet, startRow=1, startColumn=1, row.names=F, col.names=F)
addDataFrame(combined, sheet, startRow=2, startColumn=1, row.names=F, col.names=F, showNA=T)

saveWorkbook(wb, "data.xlsx")

print(linkage.file)