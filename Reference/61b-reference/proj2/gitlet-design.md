# Gitlet Design Document

## Design Document Guidelines

## 1. Classes and Data Structures

Include here any class definitions. For each class list the instance variables and static variables (if any). Include a **brief description** of each variable and its purpose in the class. Your explanations in this section should be as concise as possible. Leave the full explanation to the following sections. You may cut this section short if you find your document is too wordy.

```aidl
type cwd {
    list<untracked>
    (modified)
}

staging area = {
    map(add/modify file, blobs) (staged)
    delete set
}

type blob = array<byte>

type commit = struct {
  log message
  timestamp
  a mapping of file names to blob references
  a parent reference, 
  (for merges) a second parent reference.
}

type repository(local) = struct {
    staging area 
    blob(local)
    DAG of commit
    references = map<string, string(SHA1 of object)>
}

type repository(remote)

```
## 2. Algorithms

This is where you tell us how your code works. For each class, include a high-level description of the methods in that class. That is, do not include a line-by-line breakdown of your code, but something you would write in a javadoc comment above a method, ***including any edge cases you are accounting for\***. We have read the project spec too, so make sure you do not repeat or rephrase what is stated there.  This should be a description of how your code accomplishes what is stated in the spec.

The length of this section depends on the complexity of the task and the complexity of your design. However, simple explanations are preferred. Here are some formatting tips:

- Init
  - Create the .gitlet folder
  - Make initCommit
  - Create HEAD and master branch, stagingArea
- Add
  - File exists
  - Read lastCommit
  - add into stagedArea
- Commit
  - get lastCommit
  - get stagingArea
- For complex tasks, like determining merge conflicts, we recommend that you split the task into parts. Describe your algorithm for each part in a separate section. Start with the simplest component and build up your design, one piece at a time. For example, your algorithms section for Merge Conflicts could have sections for:
    - Checking if a merge is necessary.
    - Determining which files (if any) have a conflict.
    - Representing the conflict in the file.
- Try to clearly mark titles or names of classes with white space or some other symbols.

## 3. Persistence

Describe your strategy for ensuring that you don’t lose the state of  your program across multiple runs. Here are some tips for writing this  section:

- This section should be structured as a list of all the times you will need to record the state of the program or files. For each case, you must prove that your design ensures correct behavior. For example, explain how you intend to make sure that after we call     java gitlet.Main add wug.txt, on the next execution of     java gitlet.Main commit -m “modify wug.txt”,  the correct commit will be made.
- A good strategy for reasoning about persistence is to identify which pieces of data are needed across multiple calls to Gitlet. Then, prove that the data remains consistent for all future calls.
- This section should also include a description of your  directory and any files or subdirectories you intend on including there.

```aidl
.gitlet        
└── blobs    
    └──00
    └──01
    └──...
└── commits   
    └──00
    └──01
    └──...
└── branches   
    └──master
    └──branch1
    └──...
└── info
    └──stagingarea -> map<file, blobs>
    
```
## Example

To illustrate all this, we’ve created a [sample design document](https://sp21.datastructur.es/materials/proj/proj2/capers-example.html) for the Capers lab.