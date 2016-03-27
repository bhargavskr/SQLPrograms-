class A
{
	int i,j
	
	void show()
	{
		System.out.println(" i and j "+i+" "+j);
	}
}

class B extends A
{
	int k;
	void show()
	{
		System.out.println(" k :"+k);
	}

	void sum()
	{
	System.out.println()"i+j+k"+(i+j+k));
	}
}


class SimpleInheritance
{
	public static void main(String arg[])
	{
		A superOb=new A();
		B subOb=new B();
		
		superOb.i=7;
		superOb.j=20;
		superOb.show();
		
		subOb.i=9;
		subOb.j=87;
		subOb.k=78;
		subOb.show;
		subOb.sum;
		
	}
}	
		
		