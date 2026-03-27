// clickManager.js — новая версия, работает со slider R и draw()

window.addEventListener("DOMContentLoaded", () => {

    // ====== X-кнопки ======
    let xButtons = document.querySelectorAll('.x-button');
    xButtons.forEach(button => {
        button.addEventListener('click', event => {
            xButtons.forEach(btn => btn.classList.remove('active'));
            event.currentTarget.classList.add('active');
        });
    });
});


// ====== Изменение R (вызывается p:ajax в slider) ======
function rChange(newR) {
    let r = parseFloat(newR);

    if (!isFinite(r)) {
        console.warn("[rChange] invalid R:", newR);
        return;
    }

    console.log("[rChange] R =", r);

    // Перерисовка графика
    if (typeof window.draw === 'function') {
        window.draw(r, window.lastPoints || []);
    }
}
