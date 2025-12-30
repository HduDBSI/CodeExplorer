# Introduction

CodeExplorer is a software platform that integrates various code scanning tools. When using these code scanning tools, you need to upload the code repository to be analyzed, and then proceed through multiple steps such as code preprocessing, model training, model usage, and result visualization (each step can be used separately). By scanning the code, hidden defects (such as code cloning, feature jealousy, etc.) can be discovered, aiming to assess and improve software quality.

# Software Architecture

CodeExplorer is a multi-layered architecture. The front end is the browser, and the back end consists of a web server and a GPU server (used for model training).

# Directory Structure

This code is based on B/S (Browser/Server) architecture, comprising 1 front-end code set, 3 back-end code sets and 1 database backup file.

```
Directory Structure
├── codeexplorer-front/   #Front-end code
├── codeexplorer-server/  #Back-end server code
├── codeExplorer使用说明书.docx  #codeExplorer operation manual
└── README.md
```
