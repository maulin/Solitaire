package com.maulinpathare.solitaire;

import java.util.Stack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;

public class GameView extends View implements OnTouchListener, OnKeyListener{
	private Deck mD;
	private boolean mInit, mDone;
	private Stack<Card> mPile1, mPile2, mPile3, mPile4, mPile5, mPile6, mPile7, 
	mOpenPile, mEndPile1, mEndPile2, mEndPile3, mEndPile4, mTemp;
	private Canvas mCanvas;
	private int mPile, mOldPile, mTempSize;
	private boolean mPickUp, mVictory, mUndo;
	private Card mCard;
	private final int mSpacing = 22;
	private Paint rectPaint1, wonPaint1, wonPaint2;
	private Bitmap tsa1, tsa, tha1, tha, tca1, tca, tda1, tda;

	//called by parent view to get the measurement of a child view
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(480, 400);
	}

	public GameView(Context context) {
		super(context);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
		this.setOnTouchListener(this);
		this.setOnKeyListener(this);
		this.requestFocus();
		mD = new Deck(context, context.getResources());
		mInit = true;
		mPile1 = new Stack<Card>();
		mPile2 = new Stack<Card>();
		mPile3 = new Stack<Card>();
		mPile4 = new Stack<Card>();
		mPile5 = new Stack<Card>();
		mPile6 = new Stack<Card>();
		mPile7 = new Stack<Card>();
		mOpenPile = new Stack<Card>();
		mEndPile1 = new Stack<Card>();
		mEndPile2 = new Stack<Card>();
		mEndPile3 = new Stack<Card>();
		mEndPile4 = new Stack<Card>();
		mTemp = new Stack<Card>();
		mPickUp = false;
		mDone = false;
		mUndo = false;
		mCard = new Card(context);
		mVictory = false;
		wonPaint1 = new Paint();
		wonPaint2 = new Paint();
		rectPaint1 = new Paint();
		wonPaint1.setColor(Color.WHITE);
		wonPaint1.setTextSize(25);
		wonPaint1.setAntiAlias(true);
		wonPaint1.setFakeBoldText(true);
		wonPaint1.setTextAlign(Paint.Align.CENTER);
		wonPaint2.setColor(Color.WHITE);
		wonPaint2.setTextSize(15);
		wonPaint2.setAntiAlias(true);
		wonPaint2.setFakeBoldText(true);
		wonPaint2.setTextAlign(Paint.Align.CENTER);
		rectPaint1.setColor(Color.GREEN);
		rectPaint1.setAlpha(100);
		tsa1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.tsa);
		tsa = Bitmap.createScaledBitmap(tsa1,49, 70, true);
		tca1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.tca);
		tca = Bitmap.createScaledBitmap(tca1,49, 70, true);
		tha1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.tha);
		tha = Bitmap.createScaledBitmap(tha1,49, 70, true);
		tda1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.tda);
		tda = Bitmap.createScaledBitmap(tda1,49, 70, true);
	}

	@Override 
	protected void onDraw(Canvas canvas) {
		mCanvas = canvas;
		if(mInit){
			initialize();
			mInit = false;
		}
		if(mEndPile1.size() == 13 && mEndPile2.size() == 13 &&
				mEndPile3.size() == 13 && mEndPile4.size() == 13){
				mVictory = true;
			}
			if(mVictory == false)
			{
				mCanvas.drawColor(Color.rgb(0, 153, 51));
				if(mD.empty() == false){
					mCanvas.drawBitmap(mD.peek().getBackImage(), null, mD.peek().getRect(), null);	
				}
				Rect r = new Rect();
				mCanvas.drawRect(70, 0, 119, 70, rectPaint1);
				r.set(210, 0, 259, 70);
				mCanvas.drawBitmap(tca, null, r, null);
				r.set(280, 0, 329, 70);
				mCanvas.drawBitmap(tda, null, r, null);
				r.set(350, 0, 399, 70);
				mCanvas.drawBitmap(tsa, null, r, null);
				r.set(420, 0, 469, 70);
				mCanvas.drawBitmap(tha, null, r, null);
				drawPile(mOpenPile, 8);
				drawPile(mPile1, 1);
				drawPile(mPile2, 2);
				drawPile(mPile3, 3);
				drawPile(mPile4, 4);
				drawPile(mPile5, 5);
				drawPile(mPile6, 6);
				drawPile(mPile7, 7);
				drawPile(mEndPile1, 9);
				drawPile(mEndPile2, 10);
				drawPile(mEndPile3, 11);
				drawPile(mEndPile4, 12);
				drawPile(mTemp, 0);
				if(mPickUp){
					for(int i = mTemp.size()-1; i >= 0; i--){
						mCanvas.drawBitmap(mTemp.elementAt(i).getFrontImage(), null, 
								mTemp.elementAt(i).getRect(), null);
					}
				}
			}
			else{
				String mWon = "You Won!";
				String mNew = "Press menu to play again or exit";
				mCanvas.drawColor(Color.rgb(0, 153, 51));
				mCanvas.drawText(mWon, this.getWidth()/2 - mWon.length()/2, 100, wonPaint1);
				mCanvas.drawText(mNew, this.getWidth()/2 - mNew.length()/2, 200, wonPaint2);

			}
	}//end onDraw

	//Initialize the game
	//Create the different piles
	protected void initialize(){
		mD.shuffle();
		Card c;
		for(int i = 0; i < 7; i++){
			if(mPile1.size() < 1){
				c = mD.deal();
				c.setRect(0, 75, 49, 145);
				c.setPile(1);
				mPile1.push(c);
			}
			if(mPile2.size() < 2){
				c = mD.deal();
				c.setRect(70, 75, 119, 145);
				c.setPile(2);
				mPile2.push(c);
			}
			if(mPile3.size() < 3){
				c = mD.deal();
				c.setRect(140, 75, 189, 145);
				c.setPile(3);
				mPile3.push(c);
			}
			if(mPile4.size() < 4){
				c = mD.deal();
				c.setRect(210, 75, 259, 145);
				c.setPile(4);
				mPile4.push(c);
			}
			if(mPile5.size() < 5){
				c = mD.deal();
				c.setRect(280, 75, 329, 145);
				c.setPile(5);
				mPile5.push(c);
			}
			if(mPile6.size() < 6){
				c = mD.deal();
				c.setRect(350, 75, 399, 145);
				c.setPile(6);
				mPile6.push(c);
			}
			if(mPile7.size() < 7){
				c = mD.deal();
				c.setRect(420, 75, 469, 145);
				c.setPile(7);
				mPile7.push(c);
			}
		}//end for loop
		mPile1.peek().setFaceUp();
		mPile2.peek().setFaceUp();
		mPile3.peek().setFaceUp();
		mPile4.peek().setFaceUp();
		mPile5.peek().setFaceUp();
		mPile6.peek().setFaceUp();
		mPile7.peek().setFaceUp();
	}//end initialize
	
	@Override
	public boolean onTouch(View v, MotionEvent event){
		int eventAction = event.getAction();
		int x = Math.round(event.getX());
		int y = Math.round(event.getY());

		switch(eventAction){
		case MotionEvent.ACTION_DOWN:
			mDone = false;
			mPickUp = false;
			if(mOpenPile.empty() == false && mOpenPile.peek().getRect().contains(x, y)){
				mPile = 8;
				mTemp.push(mOpenPile.pop());
				mOldPile = 8;
				mPickUp = true;
				mUndo = true;
			}
			else if(mD.empty() && !mOpenPile.empty()){
				while(mOpenPile.empty() == false){
					mCard = mOpenPile.pop();
					mCard.setRect(0, 0, 49, 70);
					mCard.setFaceDown();
					mCard.setPile(0);
					mD.push(mCard);
				}
				mPickUp = false;
				mDone = true;
				mUndo = false;
			}
			else if (!mD.empty() && mD.peek().getRect().contains(x, y)){//check if click happens on deck
			mCard = mD.deal();
			mCard.setRect(70, 0, 119, 70);
			mCard.setmOldRect(70, 0, 119, 70);
			mCard.setPile(8);
			mCard.setFaceUp();
			mOpenPile.push(mCard);
			mPickUp = false;
			mDone = true;
			mPile = 8;
			mOldPile = 0;
			mUndo = true;
			mTempSize = 1;
			}
			else{
				checkTouch(mPile1, 1, x, y);
				checkTouch(mPile2, 2, x, y);
				checkTouch(mPile3, 3, x, y);
				checkTouch(mPile4, 4, x, y);
				checkTouch(mPile5, 5, x, y);
				checkTouch(mPile6, 6, x, y);
				checkTouch(mPile7, 7, x, y);
			}
			if(mDone == false && mPickUp == false){
				mPickUp = false;
				mDone = true;
			}
			break; //end case action_touch

		case MotionEvent.ACTION_MOVE:
			if(mPickUp && mTemp.peek().isFaceUp()){
				move(x, y);
			}
			break; //end case action_move

		case MotionEvent.ACTION_UP:
			if(mDone == false && mPickUp == true){
				check(mPile1, 1);
				check(mPile2, 2);
				check(mPile3, 3);
				check(mPile4, 4);
				check(mPile5, 5);
				check(mPile6, 6);
				check(mPile7, 7);
			}
			if(mDone == false){
				returnCard();
			}
			break; //end case action_up

		}//end switch(event)

		invalidate();
		return true;
	}//end onTouch


	private void check(Stack<Card> pile, int i){
		if(mDone == false){
			//move cards to end piles if possible
			if(mTemp.size() == 1){
				if(mTemp.peek().getSuite() == "clubs"){
					if(mEndPile1.isEmpty()){
						if(mTemp.peek().getRank() == 1){
							mPile = 9;
							mTemp.peek().setRect(210, 0, 259, 70);
							mEndPile1.push(mTemp.pop());
							mPickUp = false; mDone = true; mUndo = true;
						}
					}
					else if(mTemp.peek().getRank() - mEndPile1.peek().getRank() == 1){
						mPile = 9;
						mTemp.peek().setRect(210, 0, 259, 70);
						mEndPile1.push(mTemp.pop());
						mPickUp = false; mDone = true; mUndo = true;
					}
				}
				if(mDone == false && mTemp.peek().getSuite() == "daimonds"){
					if(mEndPile2.isEmpty()){
						if(mTemp.peek().getRank() == 1){
							mPile = 10;
							mTemp.peek().setRect(280, 0, 329, 70);
							mEndPile2.push(mTemp.pop());
							mPickUp = false; mDone = true; mUndo = true;
						}
					}
					else if(mTemp.peek().getRank() - mEndPile2.peek().getRank() == 1){
						mPile = 10;
						mTemp.peek().setRect(280, 0, 329, 70);
						mEndPile2.push(mTemp.pop());
						mPickUp = false; mDone = true; mUndo = true;
					}
				}
				if(mDone == false && mTemp.peek().getSuite() == "spades"){
					if(mEndPile3.isEmpty()){
						if(mTemp.peek().getRank() == 1){
							mPile = 11;
							mTemp.peek().setRect(350, 0, 399, 70);
							mEndPile3.push(mTemp.pop());
							mPickUp = false; mDone = true; mUndo = true;
						}
					}
					else if(mTemp.peek().getRank() - mEndPile3.peek().getRank() == 1){
						mPile = 11;
						mTemp.peek().setRect(350, 0, 399, 70);
						mEndPile3.push(mTemp.pop());
						mPickUp = false; mDone = true; mUndo = true;
					}
				}
				if(mDone == false && mTemp.peek().getSuite() == "hearts"){
					if(mEndPile4.isEmpty()){
						if(mTemp.peek().getRank() == 1){
							mPile = 12;
							mTemp.peek().setRect(420, 0, 469, 70);
							mEndPile4.push(mTemp.pop());
							mPickUp = false; mDone = true; mUndo = true;
						}
					}
					else if(mTemp.peek().getRank() - mEndPile4.peek().getRank() == 1){
						mPile = 12;
						mTemp.peek().setRect(420, 0, 469, 70);
						mEndPile4.push(mTemp.pop());
						mPickUp = false; mDone = true; mUndo = true;
					}
				}
			}//end mTemp.size == 1

			//King move to first empty pile
			if(mDone == false && mTemp.peek().getRank() == 13){
				if(pile.empty()){
					mPile = i;
					Rect t = new Rect();
					switch(i){
					case 1:
						t.set(0, 75, 49, 145);
						for(int j = mTemp.size()-1; j >= 0; j--){
							if(j < mTemp.size()-1){
								t.top += mSpacing;
								t.bottom += mSpacing;
							}
							mCard = mTemp.elementAt(j);
							mCard.setRect(t);
							mCard.setPile(i);
							mPile1.push(mCard);
							mPickUp = false; mDone = true; mUndo = true;
						}
						break;
					case 2:
						t.set(70, 75, 119, 145);
						for(int j = mTemp.size()-1; j >= 0; j--){
							if(j < mTemp.size()-1){
								t.top += mSpacing;
								t.bottom += mSpacing;
							}
							mCard = mTemp.elementAt(j);
							mCard.setRect(t);
							mCard.setPile(i);
							mPile2.push(mCard);
							mPickUp = false; mDone = true; mUndo = true;
						}
						break;
					case 3:
						t.set(140, 75, 189, 145);
						for(int j = mTemp.size()-1; j >= 0; j--){
							if(j < mTemp.size()-1){
								t.top += mSpacing;
								t.bottom += mSpacing;
							}
							mCard = mTemp.elementAt(j);
							mCard.setRect(t);
							mCard.setPile(i);
							mPile3.push(mCard);
							mPickUp = false; mDone = true; mUndo = true;
						}
						break;
					case 4:
						t.set(210, 75, 259, 145);
						for(int j = mTemp.size()-1; j >= 0; j--){
							if(j < mTemp.size()-1){
								t.top += mSpacing;
								t.bottom += mSpacing;
							}
							mCard = mTemp.elementAt(j);
							mCard.setRect(t);
							mCard.setPile(i);
							mPile4.push(mCard);
							mPickUp = false; mDone = true; mUndo = true;
						}
						break;
					case 5:
						t.set(280, 75, 329, 145);
						for(int j = mTemp.size()-1; j >= 0; j--){
							if(j < mTemp.size()-1){
								t.top += mSpacing;
								t.bottom += mSpacing;
							}
							mCard = mTemp.elementAt(j);
							mCard.setRect(t);
							mCard.setPile(i);
							mPile5.push(mCard);
							mPickUp = false; mDone = true; mUndo = true;
						}
						break;
					case 6:
						t.set(350, 75, 399, 145);
						for(int j = mTemp.size()-1; j >= 0; j--){
							if(j < mTemp.size()-1){
								t.top += mSpacing;
								t.bottom += mSpacing;
							}
							mCard = mTemp.elementAt(j);
							mCard.setRect(t);
							mCard.setPile(i);
							mPile6.push(mCard);
							mPickUp = false; mDone = true; mUndo = true;
						}
						break;
					case 7:
						t.set(420, 75, 469, 145);
						for(int j = mTemp.size()-1; j >= 0; j--){
							if(j < mTemp.size()-1){
								t.top += mSpacing;
								t.bottom += mSpacing;
							}
							mCard = mTemp.elementAt(j);
							mCard.setRect(t);
							mCard.setPile(i);
							mPile7.push(mCard);
							mPickUp = false; mDone = true; mUndo = true;
						}
						break;
					}
					mTemp.clear();
				}
			}

			//check to see if card intersects an pile
			//if it does, check to see if it is a legal move
			if(pile.empty() == false  && mDone == false){
				if(Rect.intersects(mTemp.peek().getRect(), pile.peek().getRect())){
					if(pile.peek().isFaceUp()){
						if(
								((mTemp.peek().getSuite() == "clubs" || mTemp.peek().getSuite() == "spades") &&
										(pile.peek().getSuite() == "hearts" || pile.peek().getSuite() == "daimonds")) ||
										((mTemp.peek().getSuite() == "hearts" || mTemp.peek().getSuite() == "daimonds") &&
												(pile.peek().getSuite() == "clubs" || pile.peek().getSuite() == "spades"))){
							if(pile.peek().getRank() - mTemp.peek().getRank() == 1){
								Rect t = new Rect();
								mOldPile = mTemp.peek().getPile();
								for(int j = mTemp.size()-1; j >= 0; j--){
									t.set(pile.peek().getRect());
									t.top += mSpacing;
									t.bottom += mSpacing;
									mTemp.elementAt(j).setRect(t);
									mTemp.elementAt(j).setPile(pile.peek().getPile());
									pile.push(mTemp.elementAt(j));
								}
								mPickUp = false;
								mDone = true;
								mUndo = true;
								mPile = i;
								mTemp.clear();
							}
							else{
								returnCard();
							}
						}//end suite check
						else{
							returnCard();
						}
					}// end face up check
				}// end intersect check
			}
		}// end mDone check
	}// end function check

	//return the card to its old pile if an illegal move was made
	//or add it to a new pile
	private void returnCard(){
		switch(mPile){
		case 1:
			for(int i = mTemp.size()-1; i >= 0; i--){
				mTemp.elementAt(i).setPile(mPile);
				mTemp.elementAt(i).setRect(mTemp.elementAt(i).getmOldRect());
				mTemp.elementAt(i).setFaceUp();
				mPile1.push(mTemp.elementAt(i));
			}
			mTemp.clear();
			break;
		case 2:
			for(int i = mTemp.size()-1; i >= 0; i--){
				mTemp.elementAt(i).setPile(mPile);
				mTemp.elementAt(i).setRect(mTemp.elementAt(i).getmOldRect());
				mTemp.elementAt(i).setFaceUp();
				mPile2.push(mTemp.elementAt(i));
			}
			mTemp.clear();
			break;
		case 3:
			for(int i = mTemp.size()-1; i >= 0; i--){
				mTemp.elementAt(i).setPile(mPile);
				mTemp.elementAt(i).setRect(mTemp.elementAt(i).getmOldRect());
				mTemp.elementAt(i).setFaceUp();
				mPile3.push(mTemp.elementAt(i));
			}
			mTemp.clear();
			break;
		case 4:
			for(int i = mTemp.size()-1; i >= 0; i--){
				mTemp.elementAt(i).setPile(mPile);
				mTemp.elementAt(i).setRect(mTemp.elementAt(i).getmOldRect());
				mTemp.elementAt(i).setFaceUp();
				mPile4.push(mTemp.elementAt(i));
			}
			mTemp.clear();
			break;
		case 5:
			for(int i = mTemp.size()-1; i >= 0; i--){
				mTemp.elementAt(i).setPile(mPile);
				mTemp.elementAt(i).setRect(mTemp.elementAt(i).getmOldRect());
				mTemp.elementAt(i).setFaceUp();
				mPile5.push(mTemp.elementAt(i));
			}
			mTemp.clear();
			break;
		case 6:
			for(int i = mTemp.size()-1; i >= 0; i--){
				mTemp.elementAt(i).setPile(mPile);
				mTemp.elementAt(i).setRect(mTemp.elementAt(i).getmOldRect());
				mTemp.elementAt(i).setFaceUp();
				mPile6.push(mTemp.elementAt(i));
			}
			mTemp.clear();
			break;
		case 7:
			for(int i = mTemp.size()-1; i >= 0; i--){
				mTemp.elementAt(i).setPile(mPile);
				mTemp.elementAt(i).setRect(mTemp.elementAt(i).getmOldRect());
				mTemp.elementAt(i).setFaceUp();
				mPile7.push(mTemp.elementAt(i));
			}
			mTemp.clear();
			break;
		case 8:
			for(int i = mTemp.size()-1; i >= 0; i--){
				mTemp.elementAt(i).setPile(mPile);
				mTemp.elementAt(i).setRect(mTemp.elementAt(i).getmOldRect());
				mTemp.elementAt(i).setFaceUp();
				mOpenPile.push(mTemp.elementAt(i));
			}
			mTemp.clear();
			break;
		case 9:
			for(int i = mTemp.size()-1; i >= 0; i--){
				mTemp.elementAt(i).setPile(mPile);
				mTemp.elementAt(i).setRect(mTemp.elementAt(i).getmOldRect());
				mTemp.elementAt(i).setFaceUp();
				mEndPile1.push(mTemp.elementAt(i));
			}
			mTemp.clear();
			break;
		case 10:
			for(int i = mTemp.size()-1; i >= 0; i--){
				mTemp.elementAt(i).setPile(mPile);
				mTemp.elementAt(i).setRect(mTemp.elementAt(i).getmOldRect());
				mTemp.elementAt(i).setFaceUp();
				mEndPile2.push(mTemp.elementAt(i));
			}
			mTemp.clear();
			break;
		case 11:
			for(int i = mTemp.size()-1; i >= 0; i--){
				mTemp.elementAt(i).setPile(mPile);
				mTemp.elementAt(i).setRect(mTemp.elementAt(i).getmOldRect());
				mTemp.elementAt(i).setFaceUp();
				mEndPile3.push(mTemp.elementAt(i));
			}
			mTemp.clear();
			break;
		case 12:
			for(int i = mTemp.size()-1; i >= 0; i--){
				mTemp.elementAt(i).setPile(mPile);
				mTemp.elementAt(i).setRect(mTemp.elementAt(i).getmOldRect());
				mTemp.elementAt(i).setFaceUp();
				mEndPile4.push(mTemp.elementAt(i));
			}
			mTemp.clear();
			break;
		}
		mPickUp = false;
		mDone = true;
		mUndo = true;
	}//end returnCard

	//called when the user clicks on a card
	//this function will add a single/multiple cards to mtemp 
	//depending on where the user clicks
	//if the user clicks a facedown card it is flipped
	private void checkTouch(Stack<Card> pile, int pileNo, int x, int y){
		if(pile.empty() == false){
			for(int i = pile.size()-1; i >= 0; i--){
				if(pile.elementAt(i).getRect().contains(x, y)){
					mPile = pileNo;
					mPickUp = true;
					if(pile.elementAt(i).isFaceUp()){
						for(int j = pile.size()-1; j >= i; j--){
							pile.elementAt(j).setmOldRect(pile.elementAt(j).getRect());
							pile.elementAt(j).setPile(mPile);
							mTemp.push(pile.elementAt(j));
						}
						for(int k = pile.size()-1; k >= i; k--){
							pile.removeElementAt(k);
						}
					}
					else{
						pile.elementAt(i).setFaceUp();
						pile.elementAt(i).setmOldRect(pile.elementAt(i).getRect());
						pile.elementAt(i).setPile(mPile);
						mPickUp = false;
						mDone = true;
						mUndo = false;
					}
					break;
				}
			}
		}
		if(mTemp.size() > 0){
			mTempSize = mTemp.size();
			mOldPile = mTemp.peek().getPile();
		}
	}// end checkTouch

	//move cards in the mtemp stack based on the xy coordinates
	private void move(int x, int y){
		int spacing = 0;
		for(int i = mTemp.size()-1; i >= 0; i--){
			mTemp.elementAt(i).getRect().left = x;
			mTemp.elementAt(i).getRect().top = y + spacing;    		
			mTemp.elementAt(i).getRect().right = x + 49;
			mTemp.elementAt(i).getRect().bottom = y + spacing + 70;
			spacing += mSpacing;
		}
	}

	private void drawPile(Stack<Card> pile, int pileno){
		Rect r = new Rect();
		if(pile.empty() == false){
			for(int i = 0; i < pile.size(); i++){
				if(pile.elementAt(i).isFaceUp()){
					r.set(pile.elementAt(i).getRect());
					if(pileno == 1 || pileno == 2 || pileno == 3 || pileno == 4 ||
					   pileno == 5 || pileno == 6 || pileno == 7){
						r.top += 5;
						r.bottom += 5;
					}
					mCanvas.drawBitmap(pile.elementAt(i).getFrontImage(), null,	
							r, null);
				}
				else{
					mCanvas.drawBitmap(pile.elementAt(i).getBackImage(), null, 
							pile.elementAt(i).getRect(), null);
				}
			}
		}
	}//end drawPile
	
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event){
		if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK){
			if(mUndo){
				Log.w("UNDO", mTempSize + " card from " + mPile + " to " + mOldPile);
				Stack<Card> pile = new Stack<Card>();
				Stack<Card> oldPile = new Stack<Card>();
				if(mPile == 1){
					pile = mPile1;
				}
				if(mPile == 2){
					pile = mPile2;
				}
				if(mPile == 3){
					pile = mPile3;
				}
				if(mPile == 4){
					pile = mPile4;
				}
				if(mPile == 5){
					pile = mPile5;
				}
				if(mPile == 6){
					pile = mPile6;
				}
				if(mPile == 7){
					pile = mPile7;
				}
				if(mPile == 8){
					pile = mOpenPile;
				}
				if(mPile == 9){
					pile = mEndPile1;
				}
				if(mPile == 10){
					pile = mEndPile2;
				}
				if(mPile == 11){
					pile = mEndPile3;
				}
				if(mPile == 12){
					pile = mEndPile4;
				}
				
				if(mOldPile == 1){
					oldPile = mPile1;
				}
				if(mOldPile == 2){
					oldPile = mPile2;
				}
				if(mOldPile == 3){
					oldPile = mPile3;
				}
				if(mOldPile == 4){
					oldPile = mPile4;
				}
				if(mOldPile == 5){
					oldPile = mPile5;
				}
				if(mOldPile == 6){
					oldPile = mPile6;
				}
				if(mOldPile == 7){
					oldPile = mPile7;
				}
				if(mOldPile == 8){
					oldPile = mOpenPile;
				}

				undo(pile, oldPile);
			}
			invalidate();
			return true;
		}//end KEYCODE_BACK
		return false;
	}
	
	private void undo(Stack<Card> pile, Stack<Card> oldPile){
		if(mPile == 8 && mOldPile == 0)
		{
			mCard = mOpenPile.pop();
			mCard.setFaceDown();
			mCard.setRect(0, 0, 49, 70);
			mCard.setPile(0);
			mD.push(mCard);
		}
		else{
			int pileSize = pile.size();
			for(int i = 0; i < mTempSize; i++ ){
				mCard = pile.elementAt(pileSize - mTempSize);
				mCard.setRect(mCard.getmOldRect());
				mCard.setPile(mOldPile);
				oldPile.push(mCard);
				pile.remove(pileSize - mTempSize);
			}
		}
		mUndo = false;
	}//end undo
}//end class GameView
