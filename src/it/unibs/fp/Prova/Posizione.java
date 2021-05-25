package it.unibs.fp.Prova;


import java.lang.Math;

public class Posizione {
	
	private double x;
	private double y;
	private double z;
	
    public Posizione(double _x,double _y, double _z)
    {
    	this.x=_x;
    	this.y=_y;
    	this.z=_z;	
    }

	public double distanza(Posizione p)
	{
		double xdis=Math.pow(p.getX()-x, 2);
		double ydis=Math.pow(p.getY()-y, 2);;
		double zdis=Math.pow(p.getZ()-z, 2);;
		double dist=Math.sqrt(xdis+ydis+zdis);
		return dist;
	}

	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}
}
