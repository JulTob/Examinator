OUT_DIR := out
SRC_DIR := src
DOCS_DIR := docs/javadoc
SOURCES := $(shell rg --files $(SRC_DIR) -g "*.java")

.PHONY: compile run test javadoc clean

compile:
	mkdir -p $(OUT_DIR)
	javac -encoding UTF-8 -d $(OUT_DIR) $(SOURCES)

run: compile
	java -cp $(OUT_DIR) p2.app.Main

test: compile
	java -cp $(OUT_DIR) p2.pruebas.PruebasRapidas

javadoc:
	mkdir -p $(DOCS_DIR)
	javadoc -encoding UTF-8 -d $(DOCS_DIR) $(SOURCES)

clean:
	rm -rf $(OUT_DIR) $(DOCS_DIR)
