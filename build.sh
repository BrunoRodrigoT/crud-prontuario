#!/bin/bash

set -e  # Encerra o script se algum comando falhar

echo "Limpando compilação anterior..."
rm -rf out aplicacao.jar

echo "Compilando arquivos Java..."
mkdir -p out
javac -d out $(find src -name "*.java")

echo "Empacotando .jar..."
jar cfm aplicacao.jar MANIFEST.MF -C out .

echo "✅ Build finalizado com sucesso!"
echo "▶️ Execute com: ./run.sh"
