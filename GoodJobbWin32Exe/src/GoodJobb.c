/*
 ============================================================================
 Name        : GoodJobb.c
 Author      : Alan Evans
 Version     :
 Copyright   : Alan Evans 2015
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <windows.h>

int countRequired(int argc, char *argv[]) {
	int i;
	int c = 0;
	for (i = 0; i < argc; i++) {
		c += strlen(argv[i]) + 4;
	}
	return c;
}

char *buildCommandLine(int argc, char *argv[]) {
	int i;
	int required = countRequired(argc, argv);
	char *result = malloc(required + 3);
	result[0] = '\0';
	for (i = 0; i < argc; i++) {
		strcat(result, argv[i]);
		if (i > 0)
			strcat(result, "  ");
	}
	return result;
}

char *getShortName(char *path) {
	char shortName[256];
	GetShortPathName(path, shortName, sizeof(shortName));
	return strdup(shortName);
}

#define arrayLength(array) (sizeof((array))/sizeof((array)[0]))

#define JAVA_HOME "JAVA_HOME"

int main(int argc, char *argv[]) {
	int i;
	char *java_home_path = getenv(JAVA_HOME);
	if (java_home_path == NULL) {
		printf("Environment variable %s not found\n", JAVA_HOME);
		return EXIT_FAILURE;
	}

	{
		char *javaHomeShortPath = getShortName(java_home_path);
		char *prefixes[] = { javaHomeShortPath, "\\bin\\java.exe", "-jar",
				"%ChocolateyInstall%\\lib\\goodjobb\\tools\\goodjobb.jar" };
		int len = arrayLength(prefixes) + argc - 1;
		char *p2[len];
		for (i = 0; i < arrayLength(prefixes); i++) {
			p2[i] = prefixes[i];
		}
		for (i = 1; i < argc; i++) {
			p2[i + arrayLength(prefixes) - 1] = argv[i];
		}
		{
			char shortName[256];
			char *commandLine = buildCommandLine(len, p2);
			system(commandLine);
			free(commandLine);
		}
		free(javaHomeShortPath);
	}
	return EXIT_SUCCESS;
}
