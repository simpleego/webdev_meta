document.addEventListener("DOMContentLoaded", () => {
    const coin = document.getElementById("coin");
    const slot = document.getElementById("slot");

    coin.addEventListener("dragstart", (event) => {
        event.dataTransfer.setData("text", event.target.id);
    });

    slot.addEventListener("dragover", (event) => {
        event.preventDefault(); // 기본 동작을 방지해 drop이 정상 작동하도록 설정
    });

    slot.addEventListener("drop", (event) => {
        event.preventDefault();
        const coinId = event.dataTransfer.getData("text");
        const draggedCoin = document.getElementById(coinId);

        // 동전을 투입구에 추가하고, 성공 메시지 출력
        slot.appendChild(draggedCoin);
        slot.style.border = "2px solid green";
        alert("동전이 성공적으로 투입되었습니다!");
    });
});

document.addEventListener("DOMContentLoaded", () => {
    const coin = document.getElementById("coin");
    const slot = document.getElementById("slot");

    coin.addEventListener("dragstart", (event) => {
        event.dataTransfer.setData("text", event.target.id);
    });

    slot.addEventListener("dragover", (event) => {
        event.preventDefault(); // 기본 동작 방지
    });

    slot.addEventListener("drop", (event) => {
        event.preventDefault();
        const coinId = event.dataTransfer.getData("text");
        const draggedCoin = document.getElementById(coinId);

        // 동전을 투입구에 추가
        slot.appendChild(draggedCoin);
        slot.style.border = "2px solid green";
        alert("동전이 성공적으로 투입되었습니다!");

        // 1초 후 동전 삭제
        setTimeout(() => {
            draggedCoin.remove();
        }, 1000);
    });
});