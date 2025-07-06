const SERVICE_KEY = 'E8bVYmii5LsG9pBtYVXG%2FuaDeoLrlBeGMq4NpnZ9Hjoy9GF%2FqnzyG3qkYwj%2BVlgAcEc1OC04dvP0TnMyUPhq0Q%3D%3D';

async function getWeather() {
  const city = document.getElementById('city').value;
  const coords = {
    대전: { lat: 36.3504, lon: 127.3845 },
    서울: { lat: 37.5665, lon: 126.9780 },
    부산: { lat: 35.1796, lon: 129.0756 }
  };

  const location = coords[city];
  if (!location) {
    document.getElementById('result').innerHTML = "❗ 도시명을 올바르게 입력하세요 (예: 서울, 대전)";
    return;
  }

  const { x, y } = dfs_xy_conv(location.lat, location.lon);

  const now = new Date();
  const baseDate = now.toISOString().slice(0,10).replace(/-/g, '');
  const baseTime = "0500"; // 오전 5시 기준 정보 예시

  const url = `https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?serviceKey=${SERVICE_KEY}&numOfRows=10&pageNo=1&dataType=JSON&base_date=${baseDate}&base_time=${baseTime}&nx=${x}&ny=${y}`;

  try {
    const response = await fetch(url);
    const data = await response.json();
    const items = data.response.body.items.item;

    const T1H = items.find(i => i.category === 'T1H');
    const SKY = items.find(i => i.category === 'SKY');
    const WSD = items.find(i => i.category === 'WSD');

    const result = `
      <h3>${city} 날씨 정보</h3>
      <p>🌡️ 기온: ${T1H?.fcstValue ?? 'N/A'}°C</p>
      <p>☁️ 하늘상태: ${skyToText(SKY?.fcstValue)}</p>
      <p>💨 풍속: ${WSD?.fcstValue ?? 'N/A'} m/s</p>
    `;
    document.getElementById('result').innerHTML = result;
  } catch (error) {
    document.getElementById('result').innerHTML = `⚠️ 오류 발생: ${error}`;
  }
}

function skyToText(code) {
  switch (code) {
    case "1": return "맑음";
    case "3": return "구름 많음";
    case "4": return "흐림";
    default: return "정보 없음";
  }
}