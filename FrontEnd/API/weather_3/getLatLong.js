// 대한민국 주요 지역의 위도와 경도 정보
const koreaRegions = {
  // 수도권
  서울: { lat: 37.5665, lng: 126.9780, region: '수도권' },
  인천: { lat: 37.4563, lng: 126.7052, region: '수도권' },
  수원: { lat: 37.2636, lng: 127.0286, region: '수도권' },
  성남: { lat: 37.4201, lng: 127.1262, region: '수도권' },
  고양: { lat: 37.6584, lng: 126.8320, region: '수도권' },
  
  // 강원권
  춘천: { lat: 37.8813, lng: 127.7298, region: '강원권' },
  강릉: { lat: 37.7519, lng: 128.8761, region: '강원권' },
  원주: { lat: 37.3422, lng: 127.9202, region: '강원권' },
  속초: { lat: 38.2070, lng: 128.5918, region: '강원권' },
  
  // 충청권
  대전: { lat: 36.3504, lng: 127.3845, region: '충청권' },
  청주: { lat: 36.6424, lng: 127.4890, region: '충청권' },
  천안: { lat: 36.8151, lng: 127.1139, region: '충청권' },
  충주: { lat: 36.9910, lng: 127.9259, region: '충청권' },
  
  // 경상권
  부산: { lat: 35.1796, lng: 129.0756, region: '경상권' },
  대구: { lat: 35.8714, lng: 128.6014, region: '경상권' },
  울산: { lat: 35.5384, lng: 129.3114, region: '경상권' },
  경주: { lat: 35.8562, lng: 129.2247, region: '경상권' },
  포항: { lat: 36.0190, lng: 129.3435, region: '경상권' },
  진주: { lat: 35.1800, lng: 128.1076, region: '경상권' },
  창원: { lat: 35.2279, lng: 128.6811, region: '경상권' },
  안동: { lat: 36.5684, lng: 128.7294, region: '경상권' },
  
  // 전라권
  광주: { lat: 35.1595, lng: 126.8526, region: '전라권' },
  전주: { lat: 35.8242, lng: 127.1480, region: '전라권' },
  목포: { lat: 34.8118, lng: 126.3922, region: '전라권' },
  순천: { lat: 34.9506, lng: 127.4872, region: '전라권' },
  군산: { lat: 35.9676, lng: 126.7369, region: '전라권' },
  
  // 제주권
  제주: { lat: 33.4996, lng: 126.5312, region: '제주권' },
  서귀포: { lat: 33.2541, lng: 126.5601, region: '제주권' }
};

// 지역 정보 검색 함수
function findRegion(cityName) {
  const region = koreaRegions[cityName];
  if (region) {
    return {
      city: cityName,
      ...region
    };
  }
  return null;
}

// 특정 권역의 모든 도시 반환
function getRegionCities(regionName) {
  const cities = [];
  for (const [city, info] of Object.entries(koreaRegions)) {
    if (info.region === regionName) {
      cities.push({ city, ...info });
    }
  }
  return cities;
}

// 두 지점 간의 거리 계산 (Haversine 공식)
function calculateDistance(lat1, lng1, lat2, lng2) {
  const R = 6371; // 지구 반지름 (km)
  const dLat = (lat2 - lat1) * Math.PI / 180;
  const dLng = (lng2 - lng1) * Math.PI / 180;
  const a = Math.sin(dLat/2) * Math.sin(dLat/2) +
            Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
            Math.sin(dLng/2) * Math.sin(dLng/2);
  const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
  return R * c;
}

// 특정 지점에서 가장 가까운 도시 찾기
function findNearestCity(targetLat, targetLng, excludeCity = null) {
  let nearestCity = null;
  let minDistance = Infinity;
  
  for (const [city, info] of Object.entries(koreaRegions)) {
    if (city === excludeCity) continue;
    
    const distance = calculateDistance(targetLat, targetLng, info.lat, info.lng);
    if (distance < minDistance) {
      minDistance = distance;
      nearestCity = { city, ...info, distance: Math.round(distance * 100) / 100 };
    }
  }
  
  return nearestCity;
}

// 모든 지역 정보 반환
function getAllRegions() {
  return Object.entries(koreaRegions).map(([city, info]) => ({
    city,
    ...info
  }));
}

// 사용 예시
console.log('=== 대한민국 주요 지역 위도/경도 정보 ===');

// 특정 도시 검색
console.log('\n1. 서울 정보:', findRegion('서울'));

// 수도권 도시들 검색
console.log('\n2. 수도권 도시들:', getRegionCities('수도권'));

// 서울에서 가장 가까운 다른 도시 찾기
console.log('\n3. 서울에서 가장 가까운 도시:', 
  findNearestCity(37.5665, 126.9780, '서울'));

// 모든 지역 정보 출력
console.log('\n4. 모든 지역 정보:');
getAllRegions().forEach(region => {
  console.log(`${region.city}: 위도 ${region.lat}, 경도 ${region.lng} (${region.region})`);
});

// 권역별 통계
console.log('\n5. 권역별 도시 수:');
const regionStats = {};
Object.values(koreaRegions).forEach(info => {
  regionStats[info.region] = (regionStats[info.region] || 0) + 1;
});
console.log(regionStats);

// 내보내기 (모듈 시스템 사용 시)
// export { koreaRegions, findRegion, getRegionCities, calculateDistance, findNearestCity, getAllRegions };