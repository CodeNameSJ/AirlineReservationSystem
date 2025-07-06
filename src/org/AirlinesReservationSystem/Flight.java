package org.AirlinesReservationSystem;

class Flight {
	private final String flightNo;
	private final String source;
	private final String destination;
	private final String departureTime;
	private final int totalSeatsEconomy;
	private final int totalSeatsBusiness;
	private int availableSeatsEconomy;
	private int availableSeatsBusiness;
	private double priceEconomy;
	private double priceBusiness;

	public Flight(String flightNo, String source, String destination, String departureTime, int totalSeatsEconomy, int totalSeatsBusiness, double priceEconomy, double priceBusiness) {
		this.flightNo = flightNo;
		this.source = source;
		this.destination = destination;
		this.departureTime = departureTime;
		this.totalSeatsEconomy = totalSeatsEconomy;
		this.totalSeatsBusiness = totalSeatsBusiness;
		this.availableSeatsEconomy = totalSeatsEconomy;
		this.availableSeatsBusiness = totalSeatsBusiness;
		this.priceEconomy = priceEconomy;
		this.priceBusiness = priceBusiness;
	}

	public double getPrice(SeatClass seatClass) {
		return (seatClass == SeatClass.ECONOMY) ? priceEconomy : priceBusiness;
	}

	public void setPriceEconomy(double price) {
		this.priceEconomy = price;
	}

	public void setPriceBusiness(double price) {
		this.priceBusiness = price;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public double getBaseFare() {
		return 5000.0;
	}

	public boolean hasSeatAvailable(SeatClass cls) {
		return (cls == SeatClass.ECONOMY) ? availableSeatsEconomy > 0 : availableSeatsBusiness > 0;
	}

	public void reserveSeat(SeatClass cls) {
		if (cls == SeatClass.ECONOMY) availableSeatsEconomy--;
		else availableSeatsBusiness--;
	}

	public void releaseSeat(SeatClass cls) {
		if (cls == SeatClass.ECONOMY) availableSeatsEconomy++;
		else availableSeatsBusiness++;
	}

	@Override
	public String toString() {
		return String.format("[Flight %s] %s -> %s at %s |\nEconomy: %d/%d | Business: %d/%d |\nEconomy Seat: ₹%.0f | Business Seat: ₹%.0f\n", flightNo, source, destination, departureTime, availableSeatsEconomy, totalSeatsEconomy, availableSeatsBusiness, totalSeatsBusiness, priceEconomy, priceBusiness);
	}
}