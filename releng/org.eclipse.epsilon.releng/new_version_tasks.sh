set -eu

Downloads=/home/data/httpd/download.eclipse.org/epsilon
Archives=/home/data/httpd/archive.eclipse.org/epsilon
UpdatesName=updates
JavadocsName=javadoc
StableName=stable
InterimVersion=interim
NewVersion=2.6
OldVersion=2.5
InterimJavadocs=$InterimVersion-$JavadocsName
StableJavadocs=$StableName-$JavadocsName

echo "Moving $OldVersion..." &&
mkdir -p $Archives/$OldVersion/$JavadocsName &&
cp -r $Downloads/$UpdatesName/$OldVersion/* $Archives/$OldVersion &&
mv $Downloads/$StableJavadocs/* $Archives/$OldVersion/$JavadocsName &&
rm -rf $Downloads/$StableJavadocs &&
cd $Downloads &&
echo "Copying update site..." &&
mkdir -p $UpdatesName/$NewVersion &&
cp -r $InterimVersion/* $UpdatesName/$NewVersion &&
rm -rf $UpdatesName/$NewVersion/$InterimVersion &&
if [ -e $UpdatesName/$NewVersion/epsilon-${InterimVersion}-site.zip ]; then
  mv $UpdatesName/$NewVersion/epsilon-${InterimVersion}-site.zip $UpdatesName/$NewVersion/epsilon-${NewVersion}-site.zip
fi &&
echo "Copying $JavadocsName..." &&
mkdir -p $StableJavadocs &&
cp -r $InterimJavadocs/* $StableJavadocs &&
rm -rf $StableJavadocs/$InterimJavadocs &&
echo "Adding $NewVersion to composite..." &&
cd $Downloads/$UpdatesName &&
export JAVA_HOME=/shared/common/jdk1.8.0_x64-latest
/shared/common/apache-ant-latest/bin/ant -f /shared/modeling/tools/promotion/manage-composite.xml add -Dchild.repository=$NewVersion

#cd /home/data/httpd/download.eclipse.org/epsilon/temp
#curl -o epsilon-${NewVersion}-signed.zip -F file=@epsilon-${NewVersion}-unsigned.zip http://build.eclipse.org:31338/macsign.php
#curl -o epsilon-${NewVersion}-signed.dmg -F sign=true -F source=@epsilon-${NewVersion}-signed.zip http://build.eclipse.org:31338/dmg-packager
#du -sh $Downloads
