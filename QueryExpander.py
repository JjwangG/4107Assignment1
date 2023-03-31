import fasttext

# with open('tokens.txt') as f:
#     lines = f.readlines()

# model = fasttext.train_unsupervised('tokens.txt', model='skipgram')

# model.save_model("token_model.bin")



# load FastText model
model = fasttext.load_model("C:\\Users\\mlpui\\OneDrive\\Documents\\YEAR 4\\csi 4107\\4107Assignment1\\cc.en.300.bin")

print(model.words)

print(model.labels)

# define sentence query
query = "How to train a deep learning model?"

# split query into individual words
words = query.split()

# expand query with similar words
expanded_query = []
for word in words:
    # find most similar words in set of documents
    similar_words = model.get_nearest_neighbors(word)
    # add similar words to expanded query
    expanded_query.extend(similar_words)

# remove duplicates from expanded query
expanded_query = list(set(expanded_query))

print(expanded_query)

# # use expanded query to retrieve relevant documents
# relevant_documents = []
# for document in documents:
#     if all(word in document for word in expanded_query):
#         relevant_documents.append(document)

