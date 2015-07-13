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
	for (i = 1; i < argc; i++) {
		c += strlen(argv[i]) + 1;
	}
	return c;
}

char *buildCommandLine(char *prefix, int argc, char *argv[]) {
	int i;
	int required = strlen(prefix) + countRequired(argc, argv);
	char *result = malloc(required + 1);
	result[0] = '\0';
	strcat(result, prefix);
	for (i = 1; i < argc; i++) {
		strcat(result, " ");
		strcat(result, argv[i]);
	}
	return result;
}

int main(int argc, char *argv[]) {
	char *prefix =
			"\"\"%JAVA_HOME%\\bin\\java\"\" -jar %ChocolateyInstall%\\lib\\goodjobb\\tools\\goodjobb.jar";
	char *commandLine = buildCommandLine(prefix, argc, argv);
	system(commandLine);
	free(commandLine);
	return EXIT_SUCCESS;
}
