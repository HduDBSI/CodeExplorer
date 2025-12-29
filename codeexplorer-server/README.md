<!-- PART 1 -->
# Code Explorer Backend

A backend system for code dataset management, preprocessing, model training, testing, and code clone detection.

Code Explorer Backend is a unified backend service designed for code analysis and clone detection workflows.  
It integrates dataset management, preprocessing pipelines, model training/testing, GPU monitoring, and result visualization, providing RESTful APIs for easy integration with frontend systems.

---

## Features

- Dataset, model, and task lifecycle management (CRUD)
- Asynchronous preprocessing, training, and testing tasks
- Code clone detection based on graph-based deep learning models
- GPU usage monitoring
- Experiment result visualization
- RESTful APIs for frontend or third-party integration

---

## System Architecture

```text
┌──────────────────────────────┐
│        Frontend / Client     │
└───────────────▲──────────────┘
                │ REST APIs
┌───────────────┴──────────────┐
│      Code Explorer Backend   │
│  ├── Database Management     │
│  ├── Preprocessing Service   │
│  ├── Training Service        │
│  ├── Testing Service         │
│  ├── GPU Monitor             │
│  └── Visualization Service   │
└───────────────▲──────────────┘
                │
┌───────────────┴──────────────┐
│      Deep Learning Models    │
│   (CodeGraph / PDG / CFG)    │
└──────────────────────────────┘


---

## 第 2 章：数据库管理接口（Dataset / Model / Preprocessing / Train / Test）

```markdown
<!-- PART 2 -->
## Database Management APIs

### Dataset Management

| Function | Endpoint | Method |
|--------|----------|--------|
| Get all datasets | `/dataset/getAllDatasets` | GET |
| Add dataset | `/dataset/addDataset` | POST |
| Get dataset by ID | `/dataset/getDatasetById/{id}` | GET |
| Delete dataset | `/dataset/deleteDatasetById/{id}` | DELETE |

**Dataset Fields**

- `id` (Integer): Dataset ID
- `dataset` (String): Dataset name
- `state` (Integer): 0 = unprocessed, 1 = processed
- `time` (LocalDateTime): Creation time
- `result` (String): Execution result
- `remark` (String): Remarks

---

### Model Management

| Function | Endpoint | Method |
|--------|----------|--------|
| Get all models | `/modelList/getAllModels` | GET |
| Get model by ID | `/modelList/getModelById/{id}` | GET |
| Add model | `/modelList/addModel` | POST |
| Delete model | `/modelList/deleteModelById/{id}` | DELETE |

**Model Fields**

- `id` (Integer): Model ID
- `name` (String): Model name
- `model` (String): Model path or identifier
- `summary` (String): Description
- `createTime` (LocalDateTime): Create time
- `updateTime` (LocalDateTime): Update time
- `remark` (String): Remarks

---

### Preprocessing Task Management

| Function | Endpoint | Method |
|--------|----------|--------|
| Get all tasks | `/preprocessing/getAllPreprocessing` | GET |
| Add task | `/preprocessing/addPreprocessing` | POST |
| Update task | `/preprocessing/updatePreprocessing` | PUT |
| Get task by ID | `/preprocessing/getPreprocessingById/{id}` | GET |
| Delete task | `/preprocessing/deletePreprocessing/{id}` | DELETE |

---

### Training Task Management

| Function | Endpoint | Method |
|--------|----------|--------|
| Get all tasks | `/train/getAllTrain` | GET |
| Add task | `/train/addTrain` | POST |
| Update task | `/train/updateTrain` | PUT |
| Get task by ID | `/train/getTrainById/{id}` | GET |
| Delete task | `/train/deleteTrain/{id}` | DELETE |

---

### Testing Task Management

| Function | Endpoint | Method |
|--------|----------|--------|
| Get all tasks | `/test/getAllTest` | GET |
| Add task | `/test/addTest` | POST |
| Update task | `/test/updateTest` | PUT |
| Get task by ID | `/test/getTestById/{id}` | GET |
| Delete task | `/test/deleteTest/{id}` | DELETE |

<!-- PART 3 -->
## Code Clone Detection Service (CodeGraph4CCDetector)

This module provides model execution services for training, testing, GPU monitoring, directory inspection, and result visualization.

### Directory Structure

```text
├── bigclonebenchdata/          # BigCloneBench raw dataset
├── googlejam4_src/             # GoogleJam4 raw dataset
├── DataSetJsonVec/             # Preprocessed JSON vector data
├── layers/                     # Model layers (NN components)
├── myModels/                   # Model architectures
├── saveModel/                  # Saved checkpoints
├── dic_struct.py               # Directory structure service
├── gpu_status.py               # GPU usage service
├── train.py                    # Training service
├── test.py                     # Testing service
└── Visualization.py            # Visualization service

Service Startup
nohup python -u dic_struct.py > dic_struct.log 2>&1 &
nohup python -u gpu_status.py > gpu_status.log 2>&1 &
nohup python -u train.py > train.log 2>&1 &
nohup python -u test.py > test.log 2>&1 &
nohup python -u Visualization.py > Visualization.log 2>&1 &


---

## 第 4 章：数据预处理服务 TinyPDG

```markdown
<!-- PART 4 -->
## Data Preprocessing Service (TinyPDG)

This service preprocesses datasets to generate CFG/PDG corpora and processed datasets, and synchronizes task state/results with a remote database.

### Directory Structure

```text
├── bigclonebenchdata/          # BigCloneBench raw dataset
├── googlejam4_src/             # GoogleJam4 raw dataset
├── src/                        # Preprocessing source code
├── outPut_cfg/                 # Generated CFG graphs
├── outPut_pdg/                 # Generated PDG graphs
├── process.py                  # Preprocessing service
└── upload.py                   # Dataset upload service

Service Startup
nohup python -u upload.py > upload.log 2>&1 &
nohup python -u process.py > process.log 2>&1 &
