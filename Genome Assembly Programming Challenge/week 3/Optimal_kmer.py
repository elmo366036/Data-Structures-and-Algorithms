# Uses python3
import sys

#find the largest size k-mer
#start with a large size (i.e., the length of the read)
#   and work backwards tp size 1
#create k-mers based on  this size and compare the k-mer prefixexs to suffixes
#if the prefixes and suffixes are the same for a k-mer set of lenth k,
#   we're done. the answer is k

def isOptimized(k, reads):
	#create a set of k-mers for analysis. k-mers are segments of each read
	kmers = set()
	for read in reads: 						#iterate through all reads
		#print(read) #for debug
		for i in range(0, len(read)-k+1):
			#len(read)-k+1 is the number of segments per read
			#it is based on k
			#as k decreases, number of segments per read increases
			#print(read[i:i+k]) #for debug
			kmers.add(read[i:i+k])
				#add segment of read that is k long
				#starting at position i
				#e.g., "AACG", k = 3 -> ("AAC", "ACG")
	#print(kmers) #for debug

	prefixes = set()
	suffixes = set()
	for kmer in kmers:
		#iterate through the k-mers and see if a suffix is the same
		#as a prefix.
		prefixes.add(kmer[:-1])	#add prefix of k-mer (remove last character)
		suffixes.add(kmer[1:])	#add suffix of k-mer (remove first character)
	return prefixes == suffixes #see if they are the same. if so end.

reads = []
#reads = ["AACG","ACGT","CAAC","GTTG","TGCA"] #for testing

for i in range(400):
#for i in range(5): #for debug
	read = sys.stdin.readline().strip()
	reads.append(read)

for k in range(len(reads[0]), 1, -1):
	#count down from length of reads to 1
	#since we are maximizing the kmer length,
	#   start with max comparison size and work down
	if isOptimized(k, reads):
		#if the suffix and prefix are the same, stop
		#this works because we started with a large k
		print(k)
		break
