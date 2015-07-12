#Good Jobb

Trouble with Android's Jobb tool? Or just need more control over compression.

```
Good Jobb -- Create OBB files for use on Android

 -d <directory> Use <directory> as input for OBB files
 -o <filename>  Write OBB file out to <filename>
 -o <directory> Write OBB file out to <directory>
 -v             Verbose mode
 -h             Help; this usage screen
 -pn <package>  Package name for OBB file
 -pv <version>  Package version for OBB file
 -patch         Is patch not main

0% compression options (repeatable)
 -0e <extension> Do not compress this extension. e.g. -0e .abc
 -0r <regex>     Do not compress files matching this regex. e.g. -0r .*\\file.abc
```

Compile yourself, or download [GoodJobb.jar](https://github.com/westonized/GoodJobb/raw/master/GoodJobb.jar) and use like this:

````java -jar GoodJobb.jar <args as detailed above>````
