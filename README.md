# Text Based Search Engine

As you know, most of the information you need is provided by internet search engines. One of the most
preferred search engines is still Google. The fundamental factors behind the success of Google are
quickness and correctness. How does Google retrieve quick and correct results for user queries? In this
project, you will investigate the answers of this question. You will develop a simple text-based search
engine. Your program only operates on text files that are kept in a single directory. We will provide
necessary text documents into our document database. You can use these documents for your tests.

This project will be implemented in four phases respectively:
- File Operations
- Indexing
- Query Executing
- Ranking.


Four phases of this project are:

1. Text documents include punctuation marks and stop words. Since stop words are not important,
they should be eliminated. Your program should parse the entire document to extract the
individual words. In this phase, your program should be able to print out only the words of a
particular document as the output.

2. In indexing phase, search engines process documents and map the unstructured data to a wellknown
mathematical model. You need to build an index for all documents in a specified
directory. Numbers representing occurrence counts of words in a document (i.e., frequency of a
word in a document) should be printed out as the output of this phase. Note that the data
structure chosen for indexing documents will directly affect the performance (i.e., running time)
of the search query.

3. In query executing phase, you are expected to use the index structure you have created in the
previous phase. User enters the query (e.g., java tool ) to search in a document collection. By
using the index structure above, you can reach documents without searching whole documents
again. In this phase, your program should list the documents satisfying the user query.

4. Another problem is the ranking of results generated in the query executing phase. Since your
program might return hundreds of results (i.e., documents) in a large file system. Returning the
most relevant documents in the first places can save user time (like Google generally does). So,
you should write a relevance measure to prioritize the most relevant documents into the top of
the result list. After calculating distances of the resulting documents to the given query, you
should sort documents according to these distance values, i.e., the smaller distance gets a higher
rank in the resulting list.
