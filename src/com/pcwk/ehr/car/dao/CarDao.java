/**
 * Package Name : com.pcwk.ehr.book.dao <br/>
 * Class Name: BookDao.java <br/>
 * Description:  <br/>
 * Modification imformation : <br/> 
 * ------------------------------------------<br/>
 * 최초 생성일 : 2025-04-18<br/>
 *
 * ------------------------------------------<br/>
 * @author :user
 * @since  :2024-09-09
 * @version: 0.5
 */
package com.pcwk.ehr.car.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.car.vo.CarVO;
import com.pcwk.ehr.cmn.Cardiv;
import com.pcwk.ehr.member.vo.MemberVO;

/**
 * 
 */
public class CarDao implements Cardiv<CarVO>, PLog {

	public static final String CAR_DATA = ".\\data\\Acar.csv";

	public void carDao() {

	}

	@Override
	public int doSave(CarVO dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MemberVO doSelectOne(CarVO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CarVO> doRetrieve(CarVO dto) {
		List<CarVO> cars = new ArrayList<CarVO>();
		try (BufferedReader carReader = new BufferedReader(new FileReader(CAR_DATA))) {
			String line;

			while ((line = carReader.readLine()) != null) {
				// LOG.debug(line);
				// pcwk01,이상무01,4321a,yejiad12@gmail.com,0,2025-04-18,일반 to MamberVO

				String[] dataArr = line.split(",");

				String brand = dataArr[0];
				String model = dataArr[1];
				String size = dataArr[2];
				int price = Integer.parseInt(dataArr[3]);
				String color = dataArr[4];
				String carNo = dataArr[5];
				String fuel = dataArr[6];
				int modelYear = Integer.parseInt(dataArr[7]);
				int distance = Integer.parseInt(dataArr[8]);
				boolean reserve = Boolean.parseBoolean(dataArr[9]);

				CarVO carVO = new CarVO(brand, model, size, price, color, carNo, fuel, modelYear, distance, reserve);

				cars.add(carVO);
			}

		} catch (FileNotFoundException e) {
			LOG.debug("FileNotFoundException:" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			LOG.debug("IOException:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			LOG.debug("Exception:" + e.getMessage());
			e.printStackTrace();
		}
		return cars;

	}
	//차량 정보 수정
	@Override
	public int doUpdate(List<CarVO> dto) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAR_DATA))) {
			for (CarVO m : dto) {
				String line = String.join(",", m.getBrand(), m.getModel(), m.getSize(), Integer.toString(m.getPrice()),
						m.getColor(), m.getCarNo(), m.getFuel(), Integer.toString(m.getModelYear()),
						Integer.toString(m.getDistance()), Boolean.toString(m.getReserve()));
				writer.write(line);
				writer.newLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;

	}
	//차량 정보 삭제
	@Override
	public int doDelete(List<CarVO> dto) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAR_DATA))) {
			for (CarVO m : dto) {
				String line = String.join(",", m.getBrand(), m.getModel(), m.getSize(), Integer.toString(m.getPrice()),
						m.getColor(), m.getCarNo(), m.getFuel(), Integer.toString(m.getModelYear()),
						Integer.toString(m.getDistance()), Boolean.toString(m.getReserve()));
				writer.write(line);
				writer.newLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	// 차량 예약 취소
	public boolean carReserveCancel(List<CarVO> dto) {
		boolean flag = false;
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAR_DATA))) {
			for (CarVO m : dto) {
				String line = String.join(",", m.getBrand(), m.getModel(), m.getSize(), Integer.toString(m.getPrice()),
						m.getColor(), m.getCarNo(), m.getFuel(), Integer.toString(m.getModelYear()),
						Integer.toString(m.getDistance()), Boolean.toString(m.getReserve()));
				writer.write(line);
				writer.newLine();
			}
			flag = true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	// 차량 예약
	public boolean reserve(List<CarVO> dto) {
		boolean flag = false;

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAR_DATA))) {
			for (CarVO m : dto) {
				String line = String.join(",", m.getBrand(), m.getModel(), m.getSize(), Integer.toString(m.getPrice()),
						m.getColor(), m.getCarNo(), m.getFuel(), Integer.toString(m.getModelYear()),
						Integer.toString(m.getDistance()), Boolean.toString(m.getReserve()));
				writer.write(line);
				writer.newLine();
			}
			flag = true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;

	}

}
