# Task Management App - Data Layer (Room)
## Storage Architecture
#### Room Database: Stores structured data (Users, Projects, Tasks, Attachments).

#### Filesystem: Handles unstructured file attachments (PDFs, images).

#### DataStore: Not used in current implementation (reserved for future preferences).

## Key Components
### Entities:

User, Project, Task, Attachment (see schema.png for UML).

Relationships: 1-to-Many (User→Projects), Many-to-Many (Projects↔Tasks).

### Type Converters:

List<String> ↔ String (comma-separated)

Date ↔ Long (timestamp)

### DAO Patterns:

Suspend functions for one-time reads.

Flow for real-time data observation.

# Performance Note
Raw SQL (@RawQuery) showed ~25% faster execution vs. Room's @Query for complex operations (e.g., "Projects with >3 Tasks").
# STORAGE_MATRIX.md

| Feature               | Room                          | DataStore                     | Files                     |
|-----------------------|-------------------------------|-------------------------------|---------------------------|
| **Data Type**         | Structured (SQL)              | Structured (Key-Value)        | Unstructured (Binary)     |
| **Storage Capacity**  | Medium (MBs)                  | Small (KBs)                   | Large (GBs)              |
| **ACID Support**      | Yes                           | No                            | No                       |
| **Backup Difficulty** | Medium (SQLite file)          | Easy (Proto/Preferences)      | Hard (Manual management) |
| **Example in App**    | Store Projects/Tasks          | Store user preferences        | Store image attachments  |

# Suspend DAO vs Flow 

suspend: Fetches data once (blocking call).

Flow: Emits updates whenever data changes (real-time).


# Room Schema Design (UML Summary)
Entities:

User (id: Int, name: String, email: String)

@PrimaryKey on id, @ColumnInfo(unique=true) on email.
Project (id: Int, title: String, ownerId: Int)
Foreign Key: ownerId → User(id).
Task (id: Int, description: String, projectId: Int)
Index: @Index("task_project_idx", ["projectId"]).
Attachment (id: Int, filePath: String, taskId: Int)
Relationships:
1-to-Many: User → Project (One user owns many projects).
Many-to-Many: Project ↔ Task (Via junction table or direct foreign key).

