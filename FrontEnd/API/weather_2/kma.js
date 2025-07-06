const SERVICE_KEY = 'E8bVYmii5LsG9pBtYVXG%2FuaDeoLrlBeGMq4NpnZ9Hjoy9GF%2FqnzyG3qkYwj%2BVlgAcEc1OC04dvP0TnMyUPhq0Q%3D%3D';

async function getWeather() {
  const lat = parseFloat(document.getElementById('lat').value);
  const lon = parseFloat(document.getElementById('lon').value);
  const resultDiv = document.getElementById('result');

  if (isNaN(lat) || isNaN(lon)) {
    resultDiv.innerHTML = "❗ 위도와 경도를 올바르게 입력해주세요.";
    return;
  }

  const { x, y } = dfs_xy_conv(lat, lon);

  const now = new Date();
  const baseDate = now.toISOString().slice(0,10).replace(/-/g, '');
  const baseTime = "0500"; // 정시 기준 예시

  const url = `https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?serviceKey=${SERVICE_KEY}&numOfRows=10&pageNo=1&dataType=JSON&base_date=${baseDate}&base_time=${baseTime}&nx=${x}&ny=${y}`;

  try {
    const res = await fetch(url);
    const json = await res.json();
    const items = json.response.body.items.item;

    const T1H = items.find(i => i.category === 'T1H');
    const SKY = items.find(i => i.category === 'SKY');
    const WSD = items.find(i => i.category === 'WSD');

    resultDiv.innerHTML = `
      <h3>날씨 정보</h3>
      <p>📍 위도: ${lat}, 경도: ${lon}</p>
      <p>🌡️ 기온: ${T1H?.fcstValue ?? '정보 없음'} °C</p>
      <p>☁️ 하늘 상태: ${skyToText(SKY?.fcstValue)}</p>
      <p>💨 풍속: ${WSD?.fcstValue ?? '정보 없음'} m/s</p>
    `;
  } catch (error) {
    resultDiv.innerHTML = `⚠️ API 호출 오류: ${error}`;
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
