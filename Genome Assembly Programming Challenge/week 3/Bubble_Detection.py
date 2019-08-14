# python3

import sys
import queue

#adj will have form {prefix : (suffix : #times prefix in reads, ...)}

class BubbleDetection:
    def __init__(self):
        adj = self._input()             #run input(), create deBrujin
        print(self.findBubbles(adj))    #run find bubbles


    def _input(self):
        data = list(sys.stdin.read().strip().split()) #read in all data
        k, t = int(data[0]), int(data[1]) #assign k and t
        data = data[2:]    #strip off k and t

        #k = 3 #for testing
        #t = 3 #for testing
        #data = ("AACG","AAGG","ACGT","AGGT","CGTT","GCAA","GGTT","GTTG","TGCA","TTGC")
        #print(k,t) #for debug
        #print(data) #for debug
            #
            #   {'AA' : ('AC' : 1, 'AG' : 1),       START of Bubble
            #    'AC' : ('CG' : 2),
            #    'CG' : ('GT' : 2),
            #    'AG' : ('GG' : 2),
            #    'GG' : ('GT' : 2),
            #    'GT' : ('TT' : 3),
            #    'TT' : ('TG' : 2),
            #    'GC' : ('CA' : 2),
            #    'CA' : ('AA' : 1),
            #    'TG' : ('GC' : 2)}

        adj = self.DeBrujin(k, data)
        #print(adj) #for debug
        return adj

    def DeBrujin(self, k, patterns):
        adj = dict()    #use a dictionary for adjacencies
                        #will have form {prefix : (suffix : #times prefix in reads)}
        for p in patterns:  #for each read
            l = len(p)      #l = length of read
            for i in range(l-k+1):
                    	#(l-k+1) is the number of segments per read
                    	#it is based on k
                    	#as k decreases, number of segments per read increases
                        #i is the segment number
                        #e.g., if k=3 and l=4, there are 4-3+1=2 segments per read
                #print("segment in adj " + p[i:i+k-1]) #for debug
                if p[i:i+k-1] in adj:
                        #p[i:i+k-1] is the prefix of the of the segment
                        #   in p that corresponds to i
                        #see if it is in adj
                        #if it is, don't create a new entryselfself
                        #augment the existing one
                    adj[p[i:i+k-1]][p[i+1:i+k]] = adj[p[i:i+k-1]].get(p[i+1:i+k], 0) + 1
                        #p[i:i+k-1] is the prefix, p[i+1:i+k] is the suffix
                        #increment the value of prefix:suffix by one
                        #adj[segment][next segment] = adj[segment].get(next segment, 0)+1
                else:
                        #if the segment isn't in adj
                    adj[p[i:i+k-1]] = dict()
                        #create a new dictionary for it
                    adj[p[i:i+k-1]][p[i+1:i+k]] = 1
                        #p[i:i+k-1] is the prefix, p[i+1:i+k] is the suffix
                        #set the value of prefix:suffix:_ to  one
                        #adj[segment][next segment] = 1
                if p[i+1:i+k] not in adj:
                        #if the suffix is not in adj
                    adj[p[i+1:i+k]] = dict()
                        #create a new dictionary for it
        return adj

    def findBubbles(self, adj):
        bubble = 0
        for v in adj.values(): #iterate through the values
            #print(v) #for debug
            if len(v) > 1:
                #len(v) tells us how many bubbles come from the key corresponding to v
                bubble += 1
        return bubble

if __name__ == "__main__":
    BubbleDetection()
