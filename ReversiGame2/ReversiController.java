package reversi;

public class ReversiController implements IController{

	IModel GUIModel; 
	IView GUIView; 

	public static void main(String[] args) {
	}

	@Override
	public void initialise(IModel GUIModel, IView GUIView) {
		this.GUIModel = GUIModel;
		this.GUIView = GUIView;
	}
	
	@Override
	public void startup() {
		
		for(int loop1=0;loop1<GUIModel.getBoardWidth();loop1++) {
			for(int loop2=0;loop2<GUIModel.getBoardHeight();loop2++) {
				GUIModel.setBoardContents(loop1, loop2, 0);
		} }
		
	    GUIModel.setBoardContents(3, 4, 2);
	    GUIModel.setBoardContents(3, 3, 1);
	    GUIModel.setBoardContents(4, 4, 1);
	    GUIModel.setBoardContents(4, 3, 2);
	    GUIModel.setPlayer(1);
	    GUIModel.setFinished(false);
	    GUIView.feedbackToUser(1, "White player - choose where to put your piece");
	    GUIView.feedbackToUser(2, "Black player - not your turn");
	    GUIView.refreshView();
	}

	@Override
	public void update() {
	    if (GUIModel.hasFinished()) {
	        endGame();
	        return;
	    }
	    
	    boolean playerMove = false;
	    boolean opponentMove = false;
		
	    for (int loop2 = 0; loop2 < GUIModel.getBoardHeight(); loop2++) {
	        for (int loop1 = 0; loop1 < GUIModel.getBoardWidth(); loop1++) {
	            if (potMoveValidCheck(loop1, loop2, GUIModel.getPlayer())) {
	                playerMove = true;
	            } else if (potMoveValidCheck(loop1, loop2, getP2())) {
	                opponentMove = true;
	            }
	        }
	    }
		
		if(!playerMove) {
			if(opponentMove) {
				GUIModel.setPlayer(getP2());
			}
			else {
				GUIModel.setFinished(true);
				endGame();
				return;
			}
			
		}
		
		if(GUIModel.getPlayer() == 1) {
			GUIView.feedbackToUser(1, "White player - choose where to put your piece");
			GUIView.feedbackToUser(2, "Black player - not your turn");
		}
		else {
			GUIView.feedbackToUser(1, "White player - not your turn");
			GUIView.feedbackToUser(2, "Black player - choose where to put your piece");
		}
		GUIView.refreshView();
	}
	
	public int getP2() {
		if(GUIModel.getPlayer()==1) {
			return 2;
		}
		else {
			return 1;
		}
	}

	@Override
	public void squareSelected(int player, int x, int y) {
		
		if (GUIModel.hasFinished()){
			endGame();
			return;
		}
		
		if(GUIModel.getPlayer() != player) {
			GUIView.feedbackToUser(player, "It is not your turn!");
			return;
		}
		
		if(potMoveValidCheck(x, y, player)) {
			checkAndMove(x, y, player);
			GUIModel.setBoardContents(x, y, player);
			GUIModel.setPlayer(getP2());
			update();
		}
	}
	
	@Override
	public void doAutomatedMove(int player) {
	   int var1 = -1;
	   int var2 = -1;
	   int highest = 0;
	   int count;
	      
	   for (int loopvar1 = 0 ; loopvar1 < GUIModel.getBoardWidth() ; loopvar1++) {
	      for (int loopvar2 = 0 ; loopvar2 < GUIModel.getBoardHeight() ; loopvar2++) {
	         if (potMoveValidCheck(loopvar1,loopvar2,player)) {
	            count = 0;
	              for (int dx = -1; dx <= 1; dx++) {
	                 for (int dy = -1; dy <= 1; dy++) {
	                    if (dx == 0 && dy == 0) {
	                      continue;
	                      }
	                    count += checkDirection(loopvar1, loopvar2, dx, dy, player);
	                      }
	                  }
	              if (count > highest) {
	                 highest = count;
	                 var1 = loopvar1;
	                 var2 = loopvar2;
	              }
	          }
	      }
	   }
	   if (var1 == -1 && var2 == -1) {
	     return;
	   } else {
	     squareSelected(player, var1, var2);
	   }
	 }
	
	private boolean potMoveValidCheck (int x, int y, int player) {
		int potMoves = 0;
		if(GUIModel.getBoardContents(x, y) > 0)
			return false;
		
		potMoves += northDir(x,y,player);
		potMoves += eastDir(x,y,player);
		potMoves += southDir(x,y,player);
		potMoves += westDir(x,y,player); 
		potMoves += northEast(x,y,player);
		potMoves += northWest(x,y,player);
		potMoves += southWest(x,y,player);
		potMoves += southEast(x,y,player);
		 
		if(potMoves > -0) {
			return true;
		}
		else {
			return false;
		}
	}

	private int checkDirection(int x, int y, int dx, int dy, int player) {
		/*To check General Direction*/
	    int count = 0;
	    x += dx;
	    y += dy;
	    
	    while (x >= 0 && x < GUIModel.getBoardHeight() && y >= 0 && y < GUIModel.getBoardHeight()) {
	        int cell = GUIModel.getBoardContents(x, y);
	        if (cell == player) {
	            return count;
	        } else if (cell == 0) {
	            return 0;
	        }
	        count++;
	        x += dx;
	        y += dy;
	    }
	    
	    return 0;
	}
	
	public void checkAndMove(int rows, int columns, int player) {
		if(northDir(rows,columns,player) > 0) {
		for (int loop1 = columns - 1; loop1 >= 0 && GUIModel.getBoardContents(rows, loop1) != player; loop1--) {
		    GUIModel.setBoardContents(rows, loop1, player);
			}
		}
		
		if(eastDir(rows,columns,player) > 0) {
		    for (int loop1 = rows + 1; loop1 < GUIModel.getBoardWidth() && GUIModel.getBoardContents(loop1, columns) != player; loop1++) {
		        GUIModel.setBoardContents(loop1, columns, player);
		    }
		}
		
		if(southDir(rows,columns,player) > 0) {
			for (int loop1 = columns + 1; loop1 < GUIModel.getBoardHeight() && GUIModel.getBoardContents(rows, loop1) != player; loop1++) {
				GUIModel.setBoardContents(rows, loop1, player);
			}
		}
		
		if(westDir(rows,columns,player) > 0) {
		    for (int loop1 = rows - 1; loop1 >= 0 && GUIModel.getBoardContents(loop1, columns) != player; loop1--) {
		        GUIModel.setBoardContents(loop1, columns, player);
		    }
		}
		
		if(northEast(rows,columns,player) > 0) {
		    for (int loop1 = rows + 1, loop2 = columns - 1; loop2 < GUIModel.getBoardWidth() && loop2 >= 0 && GUIModel.getBoardContents(loop1, loop2) != player; loop1++, loop2--) {
		        GUIModel.setBoardContents(loop1, loop2, player);
		    }
		}
		
		if(southEast(rows,columns,player) > 0) {
		    for (int loop1 = rows + 1, loop2 = columns + 1; loop1 < GUIModel.getBoardWidth() && loop2 < GUIModel.getBoardHeight() && GUIModel.getBoardContents(loop1, loop2) != player; loop1++, loop2++) {
		        GUIModel.setBoardContents(loop1, loop2, player);
		    }
		}
		
		if(southWest(rows,columns,player) > 0) {
		    for (int loop1 = rows - 1, loop2 = columns + 1; loop1 >= 0 && loop2 < GUIModel.getBoardHeight() && GUIModel.getBoardContents(loop1, loop2) != player; loop1--, loop2++) {
		        GUIModel.setBoardContents(loop1, loop2, player);
		    }
		}
		
		if(northWest(rows,columns,player) > 0) {
		    for (int loop1 = rows - 1, loop2 = columns - 1; loop1 >= 0 && loop2 >= 0 && GUIModel.getBoardContents(loop1, loop2) != player; loop1--, loop2--) {
		        GUIModel.setBoardContents(loop1, loop2, player);
		    }
		}
	}
	
	private void endGame() {
	    int wPieces = 0;
	    int bPieces = 0;

	    for (int loop1 = 0; loop1 < GUIModel.getBoardWidth(); loop1++) {
	        for (int loop2 = 0; loop2 < GUIModel.getBoardHeight(); loop2++) {
	            if (GUIModel.getBoardContents(loop1, loop2) == 1) {
	                wPieces++;
	            } else if (GUIModel.getBoardContents(loop1, loop2) == 2) {
	                bPieces++;
	            }
	        }
	    }

	    String message;
	    if (wPieces > bPieces) {
	        message = String.format("White won. White %d to Black %d. Reset game to replay.", wPieces, bPieces);
	    } else if (bPieces > wPieces) {
	        message = String.format("Black won. Black %d to White %d. Reset game to replay.", bPieces, wPieces);
	    } else {
	        message = String.format("Draw. Both players ended with %d pieces. Reset game to replay.", wPieces);
	    }

	    GUIView.feedbackToUser(1, message);
	    GUIView.feedbackToUser(2, message);
	}

	public int northDir(int rows, int columns, int player) {
	    boolean foundPlayerPiece = false;
	    int captured = 0;
	    
	    for (int y = columns - 1; y >= 0; y--) {
	        if (GUIModel.getBoardContents(rows, y) == player) {
	            foundPlayerPiece = true;
	            break;
	        } else if (GUIModel.getBoardContents(rows, y) == 0) {
	            break;
	        } else {
	            captured++;
	        }
	    }
	    
	    return foundPlayerPiece ? captured : 0;
	}
	
	public int eastDir(int rows, int columns, int player) {
	    boolean foundPlayerPiece = false;
	    int captured = 0;
	    
	    for (int x = rows + 1; x < GUIModel.getBoardWidth(); x++) {
	    	if (GUIModel.getBoardContents(x, columns) == player) { 
	            foundPlayerPiece = true; 
	            break; 
	    	}
	    	else if (GUIModel.getBoardContents(x, columns) == 0){ 
	            break; 
	        } 
	        else {
	            captured++;
	        }
	    } 
	    
	    return foundPlayerPiece ? captured : 0;
	}
	
	public int southDir(int rows, int columns, int player) {
	    boolean foundPlayerPiece = false;
	    int captured = 0;

	    for (int y = columns + 1; y < GUIModel.getBoardHeight(); y++) {
	    	if (GUIModel.getBoardContents(rows, y) == player) {
	        	foundPlayerPiece = true;
	            break;
	    	} else if (GUIModel.getBoardContents(rows, y) == 0) {
	            break;
	        } else {
	            captured++;
	        }
	    }

	    return foundPlayerPiece ? captured : 0;
	}
	  
	  public int westDir(int rows, int columns, int player) {
		  boolean foundPlayerPiece = false;
		  int captured = 0;
		  
		  for (int x = rows - 1; x >= 0; x--) {
			  if (GUIModel.getBoardContents(x, columns) == player) { 
				  foundPlayerPiece = true; 
				  break; 
			  } 
			  else if (GUIModel.getBoardContents(x, columns) == 0){ 
				  break; 
			  } 
			  else {
				  captured++;
			  }
		  } 
		  
		    return foundPlayerPiece ? captured : 0;
		}
	  
	  public int northEast(int rows, int columns, int player) {
		    boolean foundPlayerPiece = false;
		    int captured = 0;
		    
		    for (int x = rows + 1, y = columns - 1; x < GUIModel.getBoardWidth() && y >= 0; x++, y--) {
		    	if (GUIModel.getBoardContents(x, y) == player) {
		            foundPlayerPiece = true;
		            break;
		    	}
		        else if (GUIModel.getBoardContents(x, y) == 0) {
		            break;
		        } else {
		            captured++;
		        }
		    }
		
		    return foundPlayerPiece ? captured : 0;
		}

	  public int southEast(int rows, int columns, int player) {
		    boolean foundPlayerPiece = false;
		    int captured = 0;

		    for (int x = rows + 1, y = columns + 1; x < GUIModel.getBoardWidth() && y < GUIModel.getBoardHeight(); x++, y++) {
		        if (GUIModel.getBoardContents(x, y) == player) {
		            foundPlayerPiece = true;
		            break;
		        } else if (GUIModel.getBoardContents(x, y) == 0) {
		            break;
		        } else {
		            captured++;
		        }
		    }

		    return foundPlayerPiece ? captured : 0;
		}
	  
	  public int southWest(int rows, int columns, int player) {
		    boolean foundPlayerPiece = false;
		    int captured = 0;
		    
		    for (int x = rows - 1, y = columns + 1; x >= 0 && y < GUIModel.getBoardHeight(); x--, y++) {
		        if (GUIModel.getBoardContents(x, y) == player) {
		            foundPlayerPiece = true;
		            break;
		        }
		        else if (GUIModel.getBoardContents(x, y) == 0) {
		            break;
		        } else {
		            captured++;
		        }
		    }
		    
		    return foundPlayerPiece ? captured : 0;
		}
	  
	  public int northWest(int rows, int columns, int player) {
		    boolean foundPlayerPiece = false;
		    int captured = 0;
		    
		    for (int x = rows - 1, y = columns - 1; x >= 0 && y >= 0; x--, y--) {
		        if (GUIModel.getBoardContents(x, y) == player) {
		            foundPlayerPiece = true;
		            break;
		        } else if (GUIModel.getBoardContents(x, y) == 0) {
		            break;
		        } else {
		            captured++;
		        }
		    }
		    
		    return foundPlayerPiece ? captured : 0;
		}
}
