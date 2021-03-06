#!/bin/bash

# Script used by Travis to install all required LaTeX and LyX assets.
# You can use this as a reference when setting up your own system.

# fail the script if any command fails
set -e

##########################################################
# Doc dependencies install script                        #
# Installs all dependencies to render documentation PDFs #
# To be called from Beagle’s root folder on Travis CI    #
#                                                        #
# author: Joshua Gleitze                                 #
# author: Roman Langrehr                                 #
##########################################################


# LyX PPA for latest version
sudo add-apt-repository -y ppa:lyx-devel/release
sudo apt-get update
# Install dependency packages
sudo apt-get install -qq lyx texlive-latex-base texlive-latex-recommended texlive-latex-extra texlive-fonts-extra texlive-bibtex-extra texlive-lang-german xindy

tmpdir=`mktemp -d`
cd $tmpdir
wget https://tug.org/fonts/getnonfreefonts/install-getnonfreefonts
sudo texlua install-getnonfreefonts
sudo getnonfreefonts-sys arial-urw
cd $OLDPWD

# Copy the lyx layout file to the private lyx layouts folder
mkdir -p ~/.lyx/layouts
cp Documentation/Dependencies/sdqthesis.layout ~/.lyx/layouts
cp Documentation/Dependencies/KIT-Beamer.layout ~/.lyx/layouts

# Download the sdqthesis template, unzip it, copy the important content into the local texmf, run texhash
texmf="/usr/share/texmf/tex/latex"
sudo mkdir -p $texmf/sdqthesis

sdqthesiszip="https://sdqweb.ipd.kit.edu/mediawiki-sdq-extern/images/7/76/Ausarbeitungs-Vorlage_SDQ_2014.zip"
kitbeamerzip="https://owncloud.joshuagleitze.de/index.php/s/SXkKlV7v6vWTWaN/download"

tmpdir=`mktemp -d`
zipname="$tmpdir/sdqthesis.zip"
kitbeamerzipname="$tmpdir/kitbeamer.zip"

wget -qO- -O $zipname $sdqthesiszip && unzip $zipname -d $tmpdir
wget -qO- -O $kitbeamerzipname $kitbeamerzip && unzip $kitbeamerzipname -d $tmpdir
unzip $tmpdir/KITbase.zip -d $tmpdir
unzip $tmpdir/KITbeamer.zip -d $tmpdir

sudo cp -r $tmpdir/logos $tmpdir/sdqthesis.cls $tmpdir/title-background.pdf $tmpdir/title-background.eps $texmf/sdqthesis
sudo cp -r $tmpdir/tex/latex/KIT $texmf

sudo texhash
	
# Export a small lyx file to assert LyX is working and to make LyX read in its config
# Reduces the noice when first calling lyx in the scripts
lyx --export-to pdflatex $tmpdir/test.tex /usr/share/lyx/examples/splash.lyx

#Install Umlet
umlet="http://www.umlet.com/umlet_13_3/umlet_13.3.zip"

zipnameumlet="$tmpdir/umlet.zip"

wget -qO- -O $zipnameumlet $umlet && sudo unzip $zipnameumlet -d /opt
sudo chown -R $USER /opt/Umlet
chmod +x /opt/Umlet/umlet.sh
mkdir ~/UMLet

rm -rf $tmpdir
