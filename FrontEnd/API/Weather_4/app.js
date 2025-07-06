function getWeather(lat, lon) {
  const locationText = document.getElementById("location");
  const weatherText = document.getElementById("weather");

  locationText.textContent = `위도: ${lat.toFixed(4)}, 경도: ${lon.toFixed(4)}`;

  // ⚠️ 실제 기상청 API 요청에는 인증키와 좌표 변환이 필요합니다.
  // 아래는 API 호출 구조 예시입니다.
  const baseDate = getBaseDateTime(); // 현재 시각 기준
  const serviceKey = "E8bVYmii5LsG9pBtYVXG%2FuaDeoLrlBeGMq4NpnZ9Hjoy9GF%2FqnzyG3qkYwj%2BVlgAcEc1OC04dvP0TnMyUPhq0Q%3D%3D";
  const nx = "60"; // 예시 x 좌표
  const ny = "127"; // 예시 y 좌표

  const url = `https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst`
            + `?serviceKey=${serviceKey}`
            + `&numOfRows=10&pageNo=1`
            + `&dataType=JSON`
            + `&base_date=${baseDate.date}&base_time=${baseDate.time}`
            + `&nx=${nx}&ny=${ny}`;

  fetch(url)
    .then((res) => res.json())
    .then((data) => {
      const items = data.response.body.items.item;
      const temp = items.find(i => i.category === "T1H")?.fcstValue;
      const sky = items.find(i => i.category === "SKY")?.fcstValue;
      const wsd = items.find(i => i.category === "WSD")?.fcstValue;
     //const reh = items.find(i => i.category === "REH")?.fcstValue;

      const skyMap = { "1": "맑음 ☀️", "3": "구름 많음 ⛅", "4": "흐림 ☁️" };
      weatherText.innerHTML = `
       기온 : ${temp?temp+'℃':'정보없음'}<br />       
       습도 : ${reh?reh+'%':'정보없음'}<br />
       하늘 상태: ${skyMap[sky] || "정보 없음"}<br />
       풍속:💨  ${wsd || "정보없음"}`;
    })
    .catch((err) => {
      weatherText.textContent = "날씨 정보를 가져오는 데 실패했습니다.";
      console.error(err);
    });
}

function getBaseDateTime() {
  const now = new Date();
  now.setMinutes(now.getMinutes() - now.getMinutes() % 30); // 30분 단위로 맞춤
  const date = now.toISOString().slice(0, 10).replace(/-/g, '');
  const hours = now.getHours().toString().padStart(2, '0');
  const mins = now.getMinutes().toString().padStart(2, '0');
  return { date, time: `${hours}${mins}` };
}

navigator.geolocation.getCurrentPosition(
  (position) => {
    const lat = position.coords.latitude;
    const lon = position.coords.longitude;
    getWeather(lat, lon);
  },
  (err) => {
    document.getElementById("weather").textContent = "위치 정보를 가져올 수 없습니다.";
    console.error(err.message);
  },
  {
    enableHighAccuracy: true,
    timeout: 10000,
    maximumAge: 0
  }
);