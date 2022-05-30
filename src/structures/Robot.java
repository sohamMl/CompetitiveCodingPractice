package structures;

public class Robot {
	private int x,y,direction,width,height;
	
	
	
	public Robot(int width, int height) {
		this.width = width;
		this.height = height;
		x=0;
		y=0;
		direction = 1;
	}
	    
    public void step(int num) {
    	int remaining_length=0;
        while(num>0) {
        	if(direction == 1) {
        		remaining_length=width - x;
        	}else if(direction == 2){
        		remaining_length=height - y;
        	}else if(direction == 3) {
        		remaining_length = x;
        	}else {
        		//direction 4
        		remaining_length = y;
        	}
        }
    }
    
    public int[] getPos() {
    	int pos[] = {x,y};
        return pos;
    }
    
    public String getDir() {
        switch(direction) {
        case 1:return "East";
        case 2:return "North";
        case 3:return "West";
        case 4:return "South";
        default : return "";
        }
    }
}
