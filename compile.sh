#!/bin/bash

echo "=== BantuBencana Compilation Script ==="
echo "Cleaning previous build..."
rm -rf target/
rm -rf out/

echo "Creating output directory..."
mkdir -p out

echo "Attempting to compile with Maven..."
if mvn clean install; then
    echo "Maven build successful!"
    echo "Running application..."
    mvn javafx:run
else
    echo "Maven build failed, trying alternative compilation method..."
    
    echo "Compiling Java files..."
    javac -d out \
        -cp "src/main/resources/*:target/dependency/*" \
        src/main/java/com/bantubencana/MainApp.java \
        src/main/java/com/bantubencana/util/*.java \
        src/main/java/com/bantubencana/model/*.java \
        src/main/java/com/bantubencana/view/*.java

    if [ $? -eq 0 ]; then
        echo "Compilation successful!"
        echo "Running application..."
        java -cp "out:src/main/resources/*:target/dependency/*" com.bantubencana.MainApp
    else
        echo "Compilation failed!"
        echo "Please check the error messages above."
        exit 1
    fi
fi 