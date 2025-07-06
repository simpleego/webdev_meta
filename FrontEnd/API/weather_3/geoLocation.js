// 현재 접속한 IP 정보를 기반으로 위도와 경도를 구함

function getGeoLocation() {
  const geoLocation  = {}; 

  if ("geolocation" in navigator) {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        const lat = position.coords.latitude;
        const long = position.coords.longitude;
        geoLocation.latitude = lat;
        geoLocation.longitude = long;        
      },
      (error) => {
        console.error("위치 정보를 가져오는 데 실패했습니다.", error);
      }
    );
  } else {
    console.log("이 브라우저에서는 위치 정보 기능을 지원하지 않습니다.");
  }
  return geoLocation
}