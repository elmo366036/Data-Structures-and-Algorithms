import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import java.util.*;

public class PhoneBook {

    private FastScanner in = new FastScanner();
    // Keep list of all existing (i.e. not deleted yet) contacts.
    private List<Contact> contacts = new ArrayList<>();
    
    private HashMap<Integer, String> contactsHash = new HashMap<Integer, String>();

    public void tester(){
    //read in source
    
    }
    
    
    public static void main(String[] args) {
        new PhoneBook().processQueries();
    }

    private Query readQuery() {
        String type = in.next();
        int number = in.nextInt();
        if (type.equals("add")) {
            String name = in.next();
            return new Query(type, name, number);
        } else {
            return new Query(type, number);
        }
    }

    private void writeResponse(String response) {
        System.out.println(response);
    }


    private void processQuery(Query query) {
        //COOP this iterates through contacts
        
        if (query.type.equals("add")) {
            // if we already have contact with such number,
            // we should rewrite contact's name
            //boolean wasFound = false;
            //COOP search contactsHash for contact. if its there, replace
            contactsHash.put(query.number, query.name);
            /*            
            for (Contact contact : contacts) //iterates through contacts
                if (contact.number == query.number) {
                    contact.name = query.name;
                    wasFound = true;
                    break;
                }
            // otherwise, just add it
            if (!wasFound)
                contacts.add(new Contact(query.name, query.number));
             */

            
        } else if (query.type.equals("del")) {
            //COOP search contactsHash for contact. if its there, delete
            if (contactsHash.containsKey(query.number)){
                contactsHash.remove(query.number);
            }
            /*
            for (Iterator<Contact> it = contacts.iterator(); it.hasNext(); ) //iterates through contacts
                if (it.next().number == query.number) {
                    it.remove();
                    break;
                }
                */
        } else {
            //COOP else means find. search contactHash for the number. if its there, response = contact
            String response = "not found";            
            if (contactsHash.containsKey(query.number)){
                response = contactsHash.get(query.number);
            }
            writeResponse(response);
            /*
            String response = "not found";
            for (Contact contact: contacts)//iterates through contacts
                if (contact.number == query.number) {
                    response = contact.name;
                    break;
                }
            writeResponse(response);
            */
        }
    }

    public void processQueries() {
        int queryCount = in.nextInt();
        for (int i = 0; i < queryCount; ++i)
            processQuery(readQuery());
    }

    static class Contact {
        String name;
        int number;

        public Contact(String name, int number) {
            this.name = name;
            this.number = number;
        }
    }

    static class Query {
        String type;
        String name;
        int number;

        public Query(String type, String name, int number) {
            this.type = type;
            this.name = name;
            this.number = number;
        }

        public Query(String type, int number) {
            this.type = type;
            this.number = number;
        }
    }

    class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}
