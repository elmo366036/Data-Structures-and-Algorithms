# Uses python3
DEFAULT_READS_NUMBER = 1618
#DEFAULT_READS_NUMBER = 5 #for testing
DEFAULT_MIN_OVERLAP_LENGTH = 60
	#this is used to create k-mers
	#k-mer length = len(read) - DEFAULT_MIN_OVERLAP_LENGTH
	#for this exercise, the k-mer length is 40
	#80 is too big
#DEFAULT_MIN_OVERLAP_LENGTH = 0 #for testing
LENGTH_OF_READ = 100
#LENGTH_OF_READ = 3 #for testing

#Methodology
# 1)Use Python - easier to manipulate strings
# 2)Create Overlap Graph using PrefixTrie (from lectures)
# 3)Sort the edges of the Overlap Graph in reverse order by weight
# 4)run greedy Hamiltonian algorith
#		note that for small sample sizes it may produce cycles that
#		   are non-Hamiltonian
#5)build a string using the path and weights
#6)since the DNA is circular, trim the portion of the end that is a repeat
# 		if the beginning

class TrieNode(object):
	def __init__(self):
		self.children = {} #set
		self.indexes = [] #list
		#each node has a set of nodes it connects to (indexs) and co
class PrefixTrie(object):
	def __init__(self):
		self.root = TrieNode()

	def addPrefix(self, string, index):
		#this runs TrieConstruction from the lectures
		for end in range(DEFAULT_MIN_OVERLAP_LENGTH, len(string)):
			#iterate from a point in the string to the end of it
			reversed_prefix = string[:end][::-1]
			#take the end of the string and reverse it
			node = self.root
			for char in reversed_prefix:
				if char not in node.children:
					node.children[char] = TrieNode()
				node = node.children[char]
			node.indexes.append(index)
			#add the index to the TrieNode

	def match(self, string):
		#this runs PrefixTrieMatching from the lectures
		adjacent = []
		node = self.root
		length = 0
		for char in string[::-1]: #iterate through the string in reverse order
			if char not in node.children:
				break
			node = node.children[char]
			length += 1
			if length >= DEFAULT_MIN_OVERLAP_LENGTH and node.indexes:
				for index in node.indexes:
					adjacent.append((index, length))
		return adjacent


def generateOverlapGraph(reads):
	#define and populate prefixTrie with reads
	#instantiate one prefixTrie
	prefixTrie = PrefixTrie()
	for i, read in enumerate(reads):
		prefixTrie.addPrefix(read, i)
	#initalize a list of size reads
	adj = [[] for _ in range(len(reads))]
	#run prefixMatching on the reads and store in adj
	for i, read in enumerate(reads):
		adj[i] = prefixTrie.match(read)
	#for each node in adj, sort edges by weights in reverse order
	for l in adj:
		l.sort(key=lambda x: x[1], reverse=True)
	return adj

def printOverlapGraph(adj): #for testing
	for l in adj:
		print(l)

def buildLongestHamiltonianPath(adj):
	#for small samples this may return a path that is not a Hamiltonian Path
	#(i.e.) a cycle that does not go through all nodes
	#in this situation the while loop will go forever
	#this can happen if
	#	1) a node has two equal paths to another,
	#   2) the chosen node only has an outgoing path back to 0, and
	#	3) all nodes have not been traversed
	#
	#need to add a check to see if the cycle goes back to the starting node
	#    before it goes through all nodes so that the loop ends
	currentNode = 0#start at node 0
	traversedNodes = set([0])#start with node 0
	path = [(0, 0)]#start with edge (0,0)
		#use edges from overlap graph to create a path of nodes
	#count = 0 #debug
	while len(traversedNodes)<len(adj): #or x>15:
		#add code to break if current returns to 0
		#print(added) #debug
		for i, edge in enumerate(adj[currentNode]):
			#count += 1 #for debug
			#print(i, edge, currentNode)#this prints the path
			if edge[0] not in traversedNodes:
				path.append(edge)
				currentNode = edge[0]
				traversedNodes.add(edge[0])
				break
			#if count >= 10: break #debug
		#if count >= 10: break #debug
	return path

def assembleGenome(path, reads):
	#iterate through the path and construct a genome in order of path
	#   using overlap length to determine start position of each read
	genome = ""
	for node in path:
		genome += reads[node[0]][node[1]:]
			#take reads[node[0]] and start at position[node[1] to the end ":"
			#concatenate
			##this results in a large genome
	#need to snip off the end
	genome = genome[:-circularOverlapValue(reads[path[-1][0]], reads[0])]
		#send the last node in the path (path[-1][0]) and the first reads
		#   to stringsOverlapValue
		#remove the amount of overlap from the end of genome
	return genome

def circularOverlapValue(s,t):
	#go through the last read
	for i in range(LENGTH_OF_READ, 0, -1): #count down to 0
		if s[LENGTH_OF_READ-i:] == t[:i]: return i
			#compare end of last read with start of first read
			#return the amount of overlap
	return 0 #if no cicular overlap

reads = []#empty list
for i in range(DEFAULT_READS_NUMBER):
	reads.append(input())

#reads = ["AAC","ACG","GAA","GTT","TCG"]#for testing
#print("Input Complete")
#print(reads) #for testing
#print("list complete") #for testing

reads = list(set(reads)) #list to set removes repeats and randomizes the inputs
#print(reads) #for testing
#print("list(set(reads)) COMPLETE")

adj = generateOverlapGraph(reads)
#print("adj complete")
#printOverlapGraph(adj)

path = buildLongestHamiltonianPath(adj)
#print("path complete")
#print(path)

genome = assembleGenome(path, reads)
print(genome)
