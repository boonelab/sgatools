suppressPackageStartupMessages(library(optparse))
suppressPackageStartupMessages(library(logging))
suppressPackageStartupMessages(library(gitter))
options(warn=-1) #suppress warnings

option_list <- list(
    make_option(c("-i", "--inputfiles"), help="Input plate files (required): containing colony sizes to be normalized (comma separated for multiple files or a zip file with all input files)" ),
    make_option(c("-p", "--plateformat"), help="Plate format as comma separated rows and columns (32,48)", default="32,48", type="character"),
    
    make_option(c("-n", "--removenoise"), help="Remove noise. [default %default]", default=F, action="store_true"),
    make_option(c("-r", "--autorotate"), help="Automatically rotate images. [default %default]", default=F, action="store_true"),
    make_option(c("-I", "--inverse"), help="Inverse. [default %default]", default=F, action="store_true"),

    make_option(c("-g", "--gridfolder"), help="", default=NULL, type="character"),
    make_option(c("-d", "--datfolder"), help="", default=NULL, type="character")
)

# Parse the agruments
args = parse_args(OptionParser(option_list = option_list))

input.files = unlist(strsplit(args$inputfiles, ','))
folder = dirname(input.files[1])

setwd(folder)

plate.format = as.numeric(unlist(strsplit(args$plateformat, ',')))

gitter.batch(input.files, plate.format=plate.format, remove.noise=args$removenoise, autorotate=args$autorotate, 
    inverse=args$inverse, contrast=args$contrast, fast=args$fast, grid.save=args$gridfolder, dat.save=args$datfolder)