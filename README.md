# STORAGE_MATRIX.md

| Feature               | Room                          | DataStore                     | Files                     |
|-----------------------|-------------------------------|-------------------------------|---------------------------|
| **Data Type**         | Structured (SQL)              | Structured (Key-Value)        | Unstructured (Binary)     |
| **Storage Capacity**  | Medium (MBs)                  | Small (KBs)                   | Large (GBs)              |
| **ACID Support**      | Yes                           | No                            | No                       |
| **Backup Difficulty** | Medium (SQLite file)          | Easy (Proto/Preferences)      | Hard (Manual management) |
| **Example in App**    | Store Projects/Tasks          | Store user preferences        | Store image attachments  |

