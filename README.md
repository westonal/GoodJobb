#Good Jobb

Trouble with Android's Jobb tool? Or just need more control over compression.

```
Good Jobb -- Create OBB files for use on Android

Supported Jobb arguments:
 -d <directory> Use <directory> as input for OBB files
 -o <filename>  Write OBB file out to <filename>
 -o <directory> Write OBB file out to <directory>
 -v             Verbose mode
 -h             Help; this usage screen
 -pn <package>  Package name for OBB file
 -pv <version>  Package version for OBB file

Additional arguments:
 -patch         Is patch not main
 -res <resFile> Write a handy resource.xml file to <resFile>
 -rp <prefix>   In the -res file, prefix each string value with this <prefix>

0% compression options (repeatable)
 -0e <extension> Do not compress this extension. e.g. -0e .abc
 -0r <regex>     Do not compress files matching this regex. e.g. -0r .*\\file.abc
```

Compile yourself and use like this:

````java -jar GoodJobb.jar <args as detailed above>````

Or install via [Chocolatey](https://chocolatey.org/packages/goodjobb) and use like this:

````goodjobb <args as detailed above>````

##Example

```
C:\>goodjobb -d c:\code\Contents -o c:\code -pn com.example.project -pv 5 -0e .jpg -v
Creating zip
These patterns will not be compressed
.jpg
Files:
  background.jpg
  SomeFile.txt (compressed)
Complete
```

Creates `main.5.com.example.project.obb`

##With resource file output

```
C:\>goodjobb -d c:\code\Contents -o c:\code -pn com.example.project -pv 5 -0e .jpg -res c:\code\mainobb.xml -v
```

Creates obb zip and `mainobb.xml`:

```
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<resources>
    <integer name="main_obb_size">244</integer>
    <integer name="main_obb_version">5</integer>
    <string name="background">background.jpg</string>
    <string name="some_file">SomeFile.txt</string>
</resources>
```

Giving you all the paths as string resources plus obb version and size as int resources!

If you use these paths, then this also means accidentally excluding an item will cause a compilation error.

Need a prefix on those strings? This could be a url for `APEZProvider` use or just a prefix to separate them from asset paths. Using this for example `-rp obb:` gives:
```
    <string name="background">obb:background.jpg</string>
    <string name="some_file">obb:SomeFile.txt</string>
```

