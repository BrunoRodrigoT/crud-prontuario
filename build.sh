#!/bin/bash

echo "Compilando..."
javac -d bin src/**/*.java

echo "Empacotando .jar..."
jar cvfm aplicacao.jar MANIFEST.MF -C bin .

echo "Pronto! Execute com: java -jar aplicacao.jar"
