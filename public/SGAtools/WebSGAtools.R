library(optparse)
library(logging)
library(plyr)

# /home/sgatools/sgatools/public/SGAtools
setwd('/Users/omarwagih/Desktop/boone-summer-project-2012/web/sgatools/public/SGAtools/')

readMeLines = readLines('README_NS.txt')

print(getwd())
source('SGAtools.R')
option_list <- list(
  make_option(c("-i", "--inputfiles"), help="Input plate files (required): containing colony sizes to be normalized (comma separated for multiple files or a zip file with all input files)" ),
  make_option(c("-a", "--adfiles"), help="Array definition file paths (optional): files containing array layout of plate (comma separated for multiple files)", default=NA, type="character"),
  make_option(c("-o", "--outputdir"), help="Output directory (optional): where the normalized/scored results are saved [default %default]", default=getwd(), type="character"),
  make_option(c("-n", "--savenames"), help="Save names (optional): for input files if any different (comma separated, in same order as inputfiles)", default=NA, type="character"),
  make_option(c("-l", "--linkagecutoff"), help="Linkage cutoff (optional): specified in KB, if -1 no linkage correction is applied [default %default]", default=-1, type="integer"),
  make_option(c("-r", "--replicates"), help="Replicates (optional): value indicating number of replicates in the screen  [default %default]", default=4, type="integer"),
  make_option(c("-s", "--score"), help="Score normalized results(optional): include to score normalized results [default %default]", action="store_true", default=FALSE),
  make_option(c("-f", "--sfunction"), help="Score function used(optional): '1' for subtraction, '2' for dividing [default %default]", type="integer", default=1),
  make_option(c("-d", "--nignore"), help="Ignore normalizations/filters (optional): comma separated normalization/filter codes to be ignored.  [default %default]", default="", type="character")
)

# Parse the agruments
args=parse_args(OptionParser(option_list = option_list))

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

print(args)

# Run normalization/scoring
sgadata.r = readSGA(args$inputfiles, args$savenames, args$adfiles, args$replicates)
sgadata.ns = lapply(sgadata.r, normalizeSGA, replicates=args$replicates, linkage.cutoff=args$linkagecutoff)

# Score data if requested
if(args$score){
   sgadata.ns = scoreSGA(sgadata.ns, scoring.function=args$sfunction)
}

# Set working directory to output directory
setwd(args$outputdir)

comment.ns = paste('# Normalized and scored by SGAtools',SGATOOLS_VERSION,'on', Sys.time())
if(!args$score) comment.ns = gsub(pattern='and scored ', '', comment.ns)

# Write generated files
for(i in 1:length(sgadata.ns)){
  savename = args$savename[i]
  
  comments = comment(sgadata.r[[i]])
  comments = c(comment.ns, comments)
  # Write comments
  writeLines(comments, savename)
  
  # Write plate data
  plate.data = sgadata.ns[[i]]
  
  write.table(plate.data, savename, quote=F, row.names=F, col.names=T, sep="\t", append=T)
  
}

combined = lapply(sgadata.ns, function(plate.data){
  df = plate.data
  df$row = ceiling(df$row/sqrt(args$replicates))
  df$col = ceiling(df$col/sqrt(args$replicates))
  
  collapsed = ddply(df, c("row", "col"), function(df) { 
    c( mean(df$colonysize, na.rm=T) , 
       paste0(unique(df$plateid), collapse=';') , 
       paste0(unique(df$query), collapse=';') , 
       paste0(unique(df$array), collapse=';') , 
       mean(df$ncolonysize, na.rm=T), 
       mean(df$score, na.rm=T),
       paste0(unique(df$kvp[!is.na(df$kvp)], na.rm=T), collapse=';'),
       sd(df$score, na.rm=T)
    ) 
  })
  names(collapsed) = c('row', 'col', 'colonysize', 'plateid', 
                       'query', 'array', 'ncolonysize', 'score', 'kvp', 'sd')
  
  collapsed$kvp[collapsed$kvp == ""] = NA
  collapsed$ncolonysize[collapsed$ncolonysize == "NaN"] = NA
  collapsed$score[collapsed$score == "NaN"] = NA
  
  ind = !is.na(collapsed$sd)
  t = round(as.numeric(collapsed$sd), digits=3)
  sd.kvp = paste0('sd=', t)
  ind1 = ind & !is.na(collapsed$kvp)
  ind2 = ind & is.na(collapsed$kvp)
  collapsed$kvp[ind1] = paste0(collapsed$kvp[ind1], ';', sd.kvp[ind1])
  collapsed$kvp[ind2] = sd.kvp[ind2]
  
  collapsed = collapsed[,-ncol(collapsed)]
  
  collapsed
})

# Combined data
savename =  "combined_data.dat"
comments = c('# Combined data file', comment.ns)
writeLines(comments, savename)
write.table(do.call(rbind, combined), savename, quote=F, row.names=F, col.names=T, sep="\t", append=T)

# README
writeLines(readMeLines, 'README.txt')
