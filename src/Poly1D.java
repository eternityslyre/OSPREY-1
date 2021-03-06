import java.util.StringTokenizer;




public class Poly1D {

	static final int VALUE = 0;
	static final int DERIV = 1;
	
	String name;
	HBondEnergy.HBGeoDimType geoType;
	//Coefficients for energy function started with largest degree
	double[] coefficients;
	final int degree; //degree of the polynomial
	double xmin; //Min and max are the boundaries where the polynomial is valid
	double xmax;
	
	double min_val; //Values of energy function if outside polynomial range
	double max_val;
	double root1;
	double root2;
	
	Poly1D(String poly_name, HBondEnergy.HBGeoDimType geoType, double xmin, double xmax,
			double min_val, double max_val, double root1, double root2, int degree,
			double[] coefficients){
		
		this.name = poly_name;
		this.geoType = geoType;
		this.xmin = xmin;
		this.xmax = xmax;
		this.min_val = min_val;
		this.max_val = max_val;
		this.root1 = root1;
		this.root2 = root2;
		this.degree = degree-1;
		this.coefficients = coefficients;
		
	}
	
	Poly1D(String s){
		
		StringTokenizer st = new StringTokenizer(s);
		
		xmin = new Double(st.nextToken());
		xmax = new Double(st.nextToken());
		
		if(xmin > xmax){
			double tmp = xmin;
			xmin = xmax;
			xmax = tmp;
		}
		
		degree = new Integer(st.nextToken()) - 1;
		
		coefficients=new double[degree+1];
		for(int i=0; i<coefficients.length;i++){
			coefficients[i] = new Double(st.nextToken());
		}
		
				
		
	}
	
	void printFunction(){
		double incr = (xmax-xmin)/180;
		
		for(double i=xmin; i <=xmax; i+= incr){
			System.out.println(i+" "+getVal(i));
		}
		
	}
	
	
	////////////////////////////////////////////////////////////////////////////////
	/// //KER: Taken from Rosetta 3.4 polynomial.cc
	///	@begin operator()
	///
	/// @brief evaluate the polynomial and its derivative.
	///
	/// @detailed
	///
	/// @param  variable - [in] - evaluate polynomial(value)
	/// @param  value - [out] - returned output
	/// @param  deriv - [out] - returned output
	///
	/// @global_read
	///
	/// @global_write
	///
	/// @remarks
	///  Note the coefficients must be in reverse order: low to high
	///
	///  Polynomial value and derivative using Horner's rule
	///  value = Sum_(i = 1,...,N) [ coeff_i * variable^(i-1) ]
	///  deriv = Sum_(i = 2,...,N) [ ( i - 1 ) * coeff_i * variable^(i-2) ]
	///  JSS: Horner's rule for evaluating polynomials is based on rewriting the polynomial as:
	///  JSS: p(x)  = a0 + x*(a1 + x*(a2 + x*(...  x*(aN)...)))
	///  JSS: or value_k = a_k + x*value_k+1 for k = N-1 to 0
	///  JSS: and the derivative is
	///  JSS: deriv_k = value_k+1 + deriv_k+1 for k = N-1 to 1
	///
	/// @references
	///
	/// @authors Jack Snoeyink
	/// @authors Matthew O'Meara
	///
	/// @last_modified Matthew O'Meara
	/////////////////////////////////////////////////////////////////////////////////
	void getValueAndDeriv(double variable, double[] retArr){
		
		if(variable <= xmin){
			retArr[VALUE] = min_val;
			retArr[DERIV] = 0.0;
			return;
		}
		if(variable >= xmax){
			retArr[VALUE] = max_val;
			retArr[DERIV] = 0.0;
			return;
		}
		
		
		double value = coefficients[0];
		double deriv = 0.0;
		for(int i=1; i <= degree; i++){
			deriv *= variable;
			deriv += value;
			value *= variable;
			value += coefficients[i];
		}
		retArr[VALUE] = value;
		retArr[DERIV] = deriv;
		
	}
	
	double getVal(double variable){
		
		if(variable <= xmin){
			return min_val;
		}
		if(variable >= xmax){
			return max_val;
			
		}
		
		
		double value = coefficients[0];
		for(int i=1; i <= degree; i++){
			value *= variable;
			value += coefficients[i];
		}
		
		
		return value;
		
		
	}
	

		
	
}
