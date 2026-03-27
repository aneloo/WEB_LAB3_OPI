const dayOfWeek = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
const months = ['January', 'February', 'March', 'April', 'May', 'June',
    'July', 'August', 'September', 'October', 'November', 'December'];

window.addEventListener("DOMContentLoaded", () => {
    const clockFromDoc = document.getElementById("clock");
    const dayFromDoc = document.getElementById("dayOfWeek");

    const setTime = () => {
        const date = new Date();

        let hours = date.getHours();
        let minutes = date.getMinutes();
        let seconds = date.getSeconds();

        if (hours < 10) hours = "0" + hours;
        if (minutes < 10) minutes = "0" + minutes;
        if (seconds < 10) seconds = "0" + seconds;

        clockFromDoc.innerHTML = [hours, minutes, seconds].join(":");
        dayFromDoc.innerHTML =
            dayOfWeek[date.getDay()] + " " +
            date.getDate() + " " +
            months[date.getMonth()] + " " +
            date.getFullYear();
    };

    setTime();
    setInterval(setTime, 9000); // 9000 мс = 9 секунд
});
