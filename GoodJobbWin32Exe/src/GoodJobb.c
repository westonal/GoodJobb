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

int countRequired(int argc, char *argv[]) {
	int i;
	int c = 0;
	for (i = 0; i < argc; i++) {
		c += strlen(argv[i]) + 3;
	}
	return c;
}

char *buildCommandLine(int argc, char *argv[]) {
	int i;
	int required = countRequired(argc, argv);
	char *result = malloc(required + 3);
	result[0] = '\0';
	strcat(result, "\"");
	for (i = 0; i < argc; i++) {
		strcat(result, "\"");
		strcat(result, argv[i]);
		strcat(result, "\" ");
	}
	result[strlen(result) - 1] = '\"';
	return result;
}

#define arrayLength(array) (sizeof((array))/sizeof((array)[0]))

int main(int argc, char *argv[]) {
	int i;
	char *prefixes[] = { "%JAVA_HOME%\\bin\\java.exe", "-jar",
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
		char *commandLine = buildCommandLine(len, p2);
		system(commandLine);
		free(commandLine);
	}
	return EXIT_SUCCESS;
}
