package com;

import com.createsend.util.ApiKeyAuthenticationDetails;
import com.createsend.util.exceptions.CreateSendException;
import com.createsend.models.clients.ClientBasics;
import com.createsend.models.subscribers.*;
import com.createsend.Clients;
import com.createsend.General;
import com.createsend.Subscribers;

public class Tester {
    public static void main(String[] args) throws CreateSendException {
        ApiKeyAuthenticationDetails auth = new ApiKeyAuthenticationDetails(
            "fadb5e402a1facb2eb3e657092ec16df");
			  
       General general = new General(auth);
        ClientBasics[] clients = general.getClients();
        for (ClientBasics cl : clients) {
            Clients cls = new Clients(auth, cl.ClientID);
            for(int i=0;i<(cls.lists()).length;i++)
            {	System.out.printf("List: %s\n", cls.lists()[i].ListID);
            	System.out.printf("List: %s\n", cls.lists()[i].Name);
            	
            	
            	if((cls.lists()[i].ListID).equals("09340a3e9c4b1cb7a8918c30ebd9ebd3"))
            	{
                    Subscribers ss=new Subscribers(auth, cls.lists()[i].ListID);
                    SubscriberToAdd sm= new SubscriberToAdd();
                    sm.Name="Binay Gaur";
                    sm.EmailAddress="binaygaur@gyansha.com";
                    String sms=ss.add(sm);
                    System.out.print(sms);
            	}
            }
          
        }
     /*   Subscribers ss=new Subscribers(auth, "09340a3e9c4b1cb7a8918c30ebd9ebd3");
        SubscriberToAdd sm= new SubscriberToAdd();
        sm.Name="Akashay";
        sm.EmailAddress="AkashaySharma@gyansha.com";
        String sms=ss.add(sm);
        System.out.print(sms);
    }*/
}
}