## Introduction

CodeExplorer-front code repository

## Directory Structure

```
Directory Structure
├── public/
├── src/
│   ├── api/                    # API request encapsulation
│   ├── assets/                 # Static resources (images, fonts, etc.)
│   ├── components/             # Universal/reusable components
│   ├── css/
│   ├── mockjs/                 # Mock data configuration
│   ├── router/                 # Routing configuration (route rules & guards)
│   ├── store/                  # State management (Vuex/Pinia store)
│   ├── utils/                  # Utility functions & helper classes
│   │   ├── convertToHtml.js    # Format console output to HTML
│   │   ├── getTime.js          # Get current timestamp/datetime
│   │   ├── highlight.js        # Text/code syntax highlighting
│   │   ├── monaco-diff.vue     # Simple code content comparison/matching
│   │   └── worker.js           # Web Worker logic (background tasks)
│   ├── views/│   │   ├── home/               # Home page module
│   │   │   ├── db/             # Code dataset interface
│   │   │   └── mp/             # Model repository interface
│   │   ├── task/               # Task management module
│   │   │   ├── cITestTask.vue  # Code clone test task interface
│   │   │   ├── ldTestTask.vue
│   │   │   ├── ywTestTask.vue
│   │   │   ├── train/          # Model training task submodule
│   │   │   │   ├── cITrainTask.vue  # Code clone training task interface
│   │   │   │   ├── ldTrainTask.vue
│   │   │   │   └── ywTrainTask.vue
│   │   │   └── preTask.vue     # Preprocessing task list interface
│   │   ├── taskDisplay/        # Task result display interface
│   │   ├── DynamicTabs.vue     # Dynamic tab component
│   │   ├── HomeIndex.vue       # Home page entry component
│   │   ├── navMenu.vue         # Navigation menu configuration component
│   │   └── temporary.vue       # Monaco-diff display interface
│   ├── App.vue                 # Root component of the application
│   ├── axios.js                # API service address & request interceptor config
│   └── main.js                 # Application entry file (bootstrap logic)
├── .browserslistrc
├── .eslintrc.js
├── .gitignore
├── babel.config.js
└── jsconfig.json
```

## Deployment Process

### 2.1 Clone Repository

```
# Clone the project from GitHub
git clone https://github.com/HduDBSI/CodeExplorer.git
cd CodeExplorer
```

### 2.2 Install Dependencies

```
# Use npm (recommended)
npm install

# Or use yarn
yarn install
```

### 2.3 Port modification

Enter the port number address of the backend running program into the configuration file of axios.js.

### 2.4 Operation

```
npm run serve
```

Start the program by using the command "npm run serve" in the CodeExplorer directory; log in to http://127.0.0.1:8080/ and enter the login interface. Enter the account: bindian and the password: bindian2024 to log in.

![image.png](https://foruda.gitee.com/images/1766645462604417721/8570d9ce_13222236.png)
