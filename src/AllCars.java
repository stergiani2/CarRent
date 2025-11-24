import java.util.ArrayList;

public class AllCars {
    ArrayList<Car> allCars;
    public AllCars() {
        allCars = new ArrayList<>();
    }
    public boolean addCar(Car car) {
        for(Car c : allCars) {
            if(c.equals(car)||c.getPlate().equals(car.getPlate())||c.getId().equals(car.getId())) {
                return false;
            }
        }
        allCars.add(car);
        return true;
    }
    public Car getCar(String id) {
        for(Car c : allCars) {
            if(c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public ArrayList<Car> getCar(String carBrand, String carModel, String carColor, String carSituation) {
        ArrayList<Car> carList = new ArrayList<>();

        for(Car c : allCars) {
            boolean flag = true;
            if(carBrand!=null){
                if(!(c.getCarBrand().equals(carBrand))){
                    flag = false;
                }
            }
            if(carModel!=null){
                if(!c.getModel().equals(carModel)) {
                    flag = false;
                }
            }
            if(carColor!=null){
                if (!c.getColor().equals(carColor)) {
                    flag = false;
                }
            }
            if(carSituation!=null){
                if(!c.getSituation().equals(carSituation)) {
                    flag = false;
                }

            }
            if(flag){
                carList.add(c);
            }
        }
        return carList;
    }


}
