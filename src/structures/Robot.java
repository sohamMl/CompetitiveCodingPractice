package structures;

/*

//05/31/2022 13:16	Accepted	65 ms	50.7 MB	java
public class Robot {
	private int x,y,direction,width,height;
	private boolean stepsCalled = false;
	
	
	
	public Robot(int width, int height) {
		this.width = width-1;
		this.height = height-1;
		x=0;
		y=0;
		direction = 4;
	}
	    
    public void step(int num) {
    	
    	stepsCalled = true;
    	num = num % (2 * ( this.width + this.height));
    	
		
    	int remaining_length=0;
        while(num>0) {
        	if(direction == 1) {
        		remaining_length=width - x;
        		if(num>remaining_length) {
        			num -= remaining_length;
        			x=width;
        			direction = 2;
        		}else {
        			x += num;
        			num = 0;
        		}
        	}else if(direction == 2){
        		remaining_length=height - y;
        		if(num>remaining_length) {
        			num -= remaining_length;
        			y=height;
        			direction = 3;
        		}else {
        			y += num;
        			num = 0;
        		}
        	}else if(direction == 3) {
        		remaining_length = x;
        		if(num>remaining_length) {
        			num -= remaining_length;
        			x=0;
        			direction = 4;
        		}else {
        			x -= num;
        			num = 0;
        		}
        	}else {
        		//direction 4
        		remaining_length = y;
        		if(num>remaining_length) {
        			num -= remaining_length;
        			y=0;
        			direction = 1;
        		}else {
        			y -= num;
        			num = 0;
        		}
        	}
        }
    }
    
    public int[] getPos() {
    	int pos[] = {x,y};
        return pos;
    }
    
    public String getDir() {
    	
    	if(stepsCalled == false) return "East";
    	
        switch(direction) {
        case 1:return "East";
        case 2:return "North";
        case 3:return "West";
        case 4:return "South";
        default : return "";
        }
    }
}
*/

//better code from leetcode discussion 
//but this is slower than mine wtf


//05/31/2022 18:09	Accepted	128 ms	98.5 MB	java
public class Robot{
	// p will store the total number of steps
	int w,h,p;
	public Robot(int w, int h) {
		this.w = w-1;
		this.h = h-1;
		this.p = 0; 
	}

	public void step(int num) {
		p += num;
	}

	public int[] getPos() {
		int remaining = p % (2*(w+h));
		
		if(remaining <= w)
			return new int[] {remaining,0};
		remaining-=w;
		
		if(remaining <= h)
			return new int[] {w,remaining};
		remaining-=h;
		
		if(remaining<=w)
			return new int[] {w-remaining,h};
		remaining-=w;
		
		
		return new int[] {0,h-remaining};
		
	}

	public String getDir() {
		int pos[] = getPos();
		if ( p == 0 || (pos[0] > 0) && (pos[1] == 0 ))
			return "East";
		else if( (pos[0] == w ) && pos[1] > 0)
			return "North";
		else if( (pos[1] == h ) && pos[0] < w )
			return "West";
		else return "South";
	}
	
}

