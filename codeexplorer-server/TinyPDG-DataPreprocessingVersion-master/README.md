TinyPDG: https://github.com/YoshikiHigo/TinyPDG
=======
A data preprocessing tool for CodeGraph4CCDetector based on TinyPDG, which use TinyPDG to batch generate CFG and PDG from Java files, and train Word2Vec to save the vectorized data of CFG and PDG to JSON files.
And CodeGraph4CCDetector is available here: https://github.com/HduDBSI/CodeGraph4CCDetector

Step 1.
Run createCFGCodeJson.java to batch generate CFG files and save their vectorized data to JSON files.

Step 2.
Run createPDGCodeJson.java to batch generate PDG files and save their vectorized data to JSON files.

Note: LearnTest.java uses the corpus of CFG/PDG to train Word2Vec.

