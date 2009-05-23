package com.maulinpathare.solitaire;

import java.util.Random;
import java.util.Stack;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Deck {
	private Stack<Card> mDeck;
	private Card[] tDeck;
	private Context mContext;
	private Bitmap mBackImage;
	
	public Deck(Context context, Resources res){
		//create cards for all four suites
		Card ca = new Card(context, res, R.drawable.ca, "clubs", 1); 
		Card ck = new Card(context, res, R.drawable.ck, "clubs", 13); 
		Card cq = new Card(context, res, R.drawable.cq, "clubs", 12); 
		Card cj = new Card(context, res, R.drawable.cj, "clubs", 11); 
		Card c10 = new Card(context, res, R.drawable.c10, "clubs",10); 
		Card c9 = new Card(context, res, R.drawable.c9, "clubs", 9); 
		Card c8 = new Card(context, res, R.drawable.c8, "clubs", 8); 
		Card c7 = new Card(context, res, R.drawable.c7, "clubs", 7); 
		Card c6 = new Card(context, res, R.drawable.c6, "clubs", 6); 
		Card c5 = new Card(context, res, R.drawable.c5, "clubs", 5); 
		Card c4 = new Card(context, res, R.drawable.c4, "clubs", 4); 
		Card c3 = new Card(context, res, R.drawable.c3, "clubs", 3); 
		Card c2 = new Card(context, res, R.drawable.c2, "clubs", 2); 
		Card ha = new Card(context, res, R.drawable.ha, "hearts", 1); 
		Card hk = new Card(context, res, R.drawable.hk, "hearts", 13); 
		Card hq = new Card(context, res, R.drawable.hq, "hearts", 12); 
		Card hj = new Card(context, res, R.drawable.hj, "hearts", 11); 
		Card h10 = new Card(context, res, R.drawable.h10, "hearts",10);
		Card h9 = new Card(context, res, R.drawable.h9, "hearts", 9); 
		Card h8 = new Card(context, res, R.drawable.h8, "hearts", 8); 
		Card h7 = new Card(context, res, R.drawable.h7, "hearts", 7); 
		Card h6 = new Card(context, res, R.drawable.h6, "hearts", 6); 
		Card h5 = new Card(context, res, R.drawable.h5, "hearts", 5);
		Card h4 = new Card(context, res, R.drawable.h4, "hearts", 4);
		Card h3 = new Card(context, res, R.drawable.h3, "hearts", 3);
		Card h2 = new Card(context, res, R.drawable.h2, "hearts", 2);
		Card sa = new Card(context, res, R.drawable.sa, "spades", 1);
		Card sk = new Card(context, res, R.drawable.sk, "spades", 13);
		Card sq = new Card(context, res, R.drawable.sq, "spades", 12);
		Card sj = new Card(context, res, R.drawable.sj, "spades", 11);
		Card s10 = new Card(context, res, R.drawable.s10, "spades",10);
		Card s9 = new Card(context, res, R.drawable.s9, "spades", 9);
		Card s8 = new Card(context, res, R.drawable.s8, "spades", 8);
		Card s7 = new Card(context, res, R.drawable.s7, "spades", 7);
		Card s6 = new Card(context, res, R.drawable.s6, "spades", 6);
		Card s5 = new Card(context, res, R.drawable.s5, "spades", 5);
		Card s4 = new Card(context, res, R.drawable.s4, "spades", 4);
		Card s3 = new Card(context, res, R.drawable.s3, "spades", 3);
		Card s2 = new Card(context, res, R.drawable.s2, "spades", 2);
		Card da = new Card(context, res, R.drawable.da, "daimonds", 1);
		Card dk = new Card(context, res, R.drawable.dk, "daimonds", 13);
		Card dq = new Card(context, res, R.drawable.dq, "daimonds", 12);
		Card dj = new Card(context, res, R.drawable.dj, "daimonds", 11);
		Card d10 = new Card(context, res, R.drawable.d10, "daimonds",10);
		Card d9 = new Card(context, res, R.drawable.d9, "daimonds", 9);
		Card d8 = new Card(context, res, R.drawable.d8, "daimonds", 8);
		Card d7 = new Card(context, res, R.drawable.d7, "daimonds", 7);
		Card d6 = new Card(context, res, R.drawable.d6, "daimonds", 6);
		Card d5 = new Card(context, res, R.drawable.d5, "daimonds", 5);
		Card d4 = new Card(context, res, R.drawable.d4, "daimonds", 4);
		Card d3 = new Card(context, res, R.drawable.d3, "daimonds", 3);
		Card d2 = new Card(context, res, R.drawable.d2, "daimonds", 2);
		
		Card[] t1Deck = {ca,ck,cq,cj,c10,c9,c8,c7,c6,c5,c4,c3,c2,
						ha,hk,hq,hj,h10,h9,h8,h7,h6,h5,h4,h3,h2,
						sa,sk,sq,sj,s10,s9,s8,s7,s6,s5,s4,s3,s2,
						da,dk,dq,dj,d10,d9,d8,d7,d6,d5,d4,d3,d2};
		tDeck = t1Deck;
		mDeck = new Stack<Card>();
		mContext = context;
		Bitmap bm = BitmapFactory.decodeResource(res, R.drawable.bb1);
		mBackImage = Bitmap.createScaledBitmap(bm,49, 70, true);
	}//end class Deck constructor
	
	public void shuffle(){
		Random randGen = new Random();
		int r;
		Card tempCard = new Card(mContext);
		
		for(int j = 0; j < 7; j++){
			for(int i = 0; i < 52; i++){
				r = randGen.nextInt(52);
				tempCard = tDeck[i];
				tDeck[i] = tDeck[r];
				tDeck[r] = tempCard;			
			}//shuffle deck
		}//do it 7 times
		
		for(int i = 0; i < 52; i++){
			mDeck.push(tDeck[i]);
		}		
	}//end class Deck shuffle
	
	public Card deal(){
		return mDeck.pop();
	}
	public Card peek(){
		return mDeck.peek();
	}
	public void push(Card card){
		mDeck.push(card);
	}
	public boolean empty(){
		return mDeck.empty();
	}
	public Bitmap getBackImage(){
		return mBackImage;
	}

}//end class Deck
 
