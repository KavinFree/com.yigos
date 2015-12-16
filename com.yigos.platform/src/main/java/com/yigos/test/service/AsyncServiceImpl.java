package com.yigos.test.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import com.yigos.framework.service.BaseServiceImpl;

@Service
public class AsyncServiceImpl extends BaseServiceImpl  {
	private HashMap<String, Integer> cache = new HashMap<String, Integer>();

	public HashMap<String, Integer> getCache() {
		return cache;
	}

	public void setCache(HashMap<String, Integer> cache) {
		this.cache = cache;
	}

	public void loadDataForInit() {
		if (this.cache.isEmpty()) {

		}
	}

	@Async
	public Future<?> loadDataToCache(Map<String, String[]> paraMap) {
		if (this.cache.isEmpty()) {

		}
		return new AsyncResult<String>("ok");
	}

	public void saveDataForDestroy(){
		List<Integer> list = new ArrayList<Integer>();
		for(Map.Entry<String, Integer> entry:cache.entrySet()){
			list.add(entry.getValue());
		}
		//this.dao.updateBatch(list);
	}
}
