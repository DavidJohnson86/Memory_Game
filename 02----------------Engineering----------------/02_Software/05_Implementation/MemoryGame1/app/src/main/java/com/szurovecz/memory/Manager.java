package com.szurovecz.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class Manager extends Activity {
    private static int ROW_COUNT = -1;
	private static int COL_COUNT = -1;
	private Context context;
	private Drawable backImage;
	private int [] [] cards;
	private List<Drawable> images;
	private Card firstCard;
	private Card seconedCard;
	private ButtonListener buttonListener;
	
	private static Object lock = new Object();
	
	private int turns;
	private int score;
	private TableLayout mainTable;
	private UpdateCardsHandler handler;
	public int remain ;
	MediaPlayer mysound;
	private TextView message;



	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mysound = MediaPlayer.create(this,R.raw.click);
      
        
        handler = new UpdateCardsHandler();
        loadImages();
        setContentView(R.layout.main);



		// Menu Selection

		((TextView)findViewById(R.id.four)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {newGame(4, 4);;

			}


		});
		((TextView)findViewById(R.id.five)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				newGame(4,5);;

			}


		});
		((TextView)findViewById(R.id.six)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				newGame(4,6);;

			}


		});
		((TextView)findViewById(R.id.seven)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				newGame(4,7);;

			}


		});


       backImage =  getResources().getDrawable(R.drawable.icon);
       
       buttonListener = new ButtonListener();
       mainTable = (TableLayout)findViewById(R.id.TableLayout03);
       context  = mainTable.getContext();
        

    }



    public void newGame(int r, int c) {
		COL_COUNT = c;
    	ROW_COUNT = r;
		remain = r*c;
    	cards = new int [COL_COUNT] [ROW_COUNT];

		mainTable.removeView(findViewById(R.id.buttons));


		mainTable.removeView(findViewById(R.id.TableRow01));
    	mainTable.removeView(findViewById(R.id.TableRow02));

    	TableRow tr = ((TableRow)findViewById(R.id.TableRow03));
    	tr.removeAllViews();

    	mainTable = new TableLayout(context);
    	tr.addView(mainTable);
    	
    	 for (int y = 0; y < ROW_COUNT; y++) {
    		 mainTable.addView(createRow(y),new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));

          }
    	 
    	 firstCard=null;
    	 loadCards();
    	 
    	 turns=0;

    	 ((TextView)findViewById(R.id.tv1)).setText("Tries: " + turns);

    	 
			
	}




    private void loadImages() {
    	images = new ArrayList<Drawable>();
    	
    	images.add(getResources().getDrawable(R.drawable.card1));
    	images.add(getResources().getDrawable(R.drawable.card2));
    	images.add(getResources().getDrawable(R.drawable.card3));
    	images.add(getResources().getDrawable(R.drawable.card4));
    	images.add(getResources().getDrawable(R.drawable.card5));
    	images.add(getResources().getDrawable(R.drawable.card6));
    	images.add(getResources().getDrawable(R.drawable.card7));
    	images.add(getResources().getDrawable(R.drawable.card8));
    	images.add(getResources().getDrawable(R.drawable.card9));
    	images.add(getResources().getDrawable(R.drawable.card10));
    	images.add(getResources().getDrawable(R.drawable.card11));
    	images.add(getResources().getDrawable(R.drawable.card12));
    	images.add(getResources().getDrawable(R.drawable.card13));
    	images.add(getResources().getDrawable(R.drawable.card14));
    	images.add(getResources().getDrawable(R.drawable.card15));
    	images.add(getResources().getDrawable(R.drawable.card16));
    	images.add(getResources().getDrawable(R.drawable.card17));
    	images.add(getResources().getDrawable(R.drawable.card18));
    	images.add(getResources().getDrawable(R.drawable.card19));
    	images.add(getResources().getDrawable(R.drawable.card20));
    	images.add(getResources().getDrawable(R.drawable.card21));
		
	}

	private void loadCards(){
		try{
	    	int size = ROW_COUNT*COL_COUNT;
	    	
	    	Log.i("loadCards()","size=" + size);
	    	
	    	ArrayList<Integer> list = new ArrayList<Integer>();
	    	
	    	for(int i=0;i<size;i++){
	    		list.add(new Integer(i));
	    	}
	    	
	    	
	    	Random r = new Random();
	    
	    	for(int i=size-1;i>=0;i--){
	    		int t=0;
	    		
	    		if(i>0){
	    			t = r.nextInt(i);
	    		}
	    		
	    		t=list.remove(t).intValue();
	    		cards[i%COL_COUNT][i/COL_COUNT]=t%(size/2);
	    		
	    		Log.i("loadCards()", "card["+(i%COL_COUNT)+
	    				"]["+(i/COL_COUNT)+"]=" + cards[i%COL_COUNT][i/COL_COUNT]);
	    	}
	    }
		catch (Exception e) {
			Log.e("loadCards()", e+"");
		}
		
    }
    
    private TableRow createRow(int y){
    	 TableRow row = new TableRow(context);
    	 row.setHorizontalGravity(Gravity.CENTER);
         
         for (int x = 0; x < COL_COUNT; x++) {
		         row.addView(createImageButton(x,y), new TableRow.LayoutParams(69, 69));
         }
         return row;
    }
    
    private View createImageButton(int x, int y){
		// Create Buttons
    	Button button = new Button(context);
    	button.setBackgroundDrawable(backImage);
    	button.setId(100*x+y);
    	button.setOnClickListener(buttonListener);
    	return button;
    }
    
    class ButtonListener implements OnClickListener {
	// When user Clicks on a Button
		@Override


		public void onClick(View v) {

			synchronized (lock) {
				if(firstCard!=null && seconedCard != null){
					return;
				}
				int id = v.getId();
				int x = id/100;
				int y = id%100;
				turnCard((Button) v, x, y);

			}
			
		}



		private void turnCard(Button button,int x, int y) {

			//final MediaPlayer mp = MediaPlayer.create(getBaseContext(),R.raw.click);
			//mp.start();


			button.setBackgroundDrawable(images.get(cards[x][y]));
			
			if(firstCard==null) {
				firstCard = new Card(button,x,y);
			}
			else{
				//the user pressed the same card
				if(firstCard.x == x && firstCard.y == y){
					return;
				}
					
				seconedCard = new Card(button,x,y);
				
				turns++;
				((TextView)findViewById(R.id.tv1)).setText("Cards: "+remain);
				((TextView)findViewById(R.id.tv1)).setText("Tries: "+turns);

			
				TimerTask tt = new TimerTask() {
					
					@Override
					public void run() {
						try{
							synchronized (lock) {
							  handler.sendEmptyMessage(0);
							}
						}
						catch (Exception e) {
							Log.e("E1", e.getMessage());
						}
					}
				};
				
				  Timer t = new Timer(false);
			        t.schedule(tt, 1300);
			}
			
				
		   }
			
		}
    // This class is responsible for handle the Cards
    class UpdateCardsHandler extends Handler{

		private TableLayout finishTable;

    	
    	@Override
    	public void handleMessage(Message msg) {
    		synchronized (lock) {
    			checkCards();
    		}
    	}
		//Check cards here
    	 public void checkCards(){

			 	// If the User found a Card
    	    	if(cards[seconedCard.x][seconedCard.y] == cards[firstCard.x][firstCard.y]){
					mysound.start();
    				firstCard.button.setVisibility(View.INVISIBLE);
    				seconedCard.button.setVisibility(View.INVISIBLE);
					remain-=2;
    			}
    			else {
    				seconedCard.button.setBackgroundDrawable(backImage);
    				firstCard.button.setBackgroundDrawable(backImage);
    			}

			 if(remain == 16){
				 /* If the user found all the card
				  Remove all Unecessary things*/
				 TableRow tr = ((TableRow)findViewById(R.id.TableRow03));
				 tr.removeAllViews();
				 TextView tv1 = ((TextView)findViewById(R.id.tv1));
				 tv1.setVisibility(View.GONE);

				 // Calculate Scores
				 score = (48 - turns) * 10;
				 if (score < 0){
					 score = 0;
				 }
				 // Create Score Screen
				 finishTable = (TableLayout)findViewById(R.id.TableLayout03);
				 RelativeLayout tR = new RelativeLayout(context);
				 tR.setPadding(5, 5, 5, 5);

				 TextView tV_txt1 = new TextView(getApplicationContext());
				 TextView tV_txt2 = new TextView(context);


				 // YOU WON YOUR SCORE IS
				 tV_txt1.setTextColor(Color.rgb(255, 255, 255));
				 tV_txt1.setTextSize(25.0f);
				 tV_txt1.setText("   Congratulations You Won. Your Score is : " + score);
				 // RETRY ?
				 tV_txt2.setTextColor(Color.rgb(255, 255, 255));
				 tV_txt2.setTextSize(25.0f);
				 tV_txt2.setText("\n\t\t\t\t                         RETRY ");

				 // Add the to the view

				 tR.addView(tV_txt1);
				 tR.addView(tV_txt2);


				 // RESTART GAME
				 tV_txt2.setOnClickListener(new OnClickListener() {
					 @Override
					 public void onClick(View v) {
						 finish();
						 startActivity(getIntent());
					 }
				 });
				 finishTable.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
				 finishTable.addView(tR);




			 }

    	    	firstCard=null;
    			seconedCard=null;
    	    }
    }
    
   
    
    
}