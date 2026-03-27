// Максимальное значение радиуса из условия
const MAX_R = 4;

// "дефолтный" и текущий R
let defaultR = 2;
let currentR = defaultR;

// массив уже отрисованных точек
let currentPoints = [];

// canvas и параметры
let canvasPlot;
let ctx;
let canvasPlotWidth = 0;
let canvasPlotHeight = 0;
let xAxis = 0;
let yAxis = 0;

// масштаб по осям
let scaleX = 30;
let scaleY = 30;

// смещения подписей
const shiftNames = 5;
const shiftAxisNames = 20;

// отступ фигуры от краёв и минимальный масштаб
const padding = 20;
const MIN_SCALE = 28;

// ====================== INIT ==========================
window.addEventListener("DOMContentLoaded", () => {
    canvasPlot = document.getElementById("canvas");
    if (!canvasPlot) return;

    ctx = canvasPlot.getContext("2d");

    recalcGeometry();
    draw();

    canvasPlot.addEventListener("click", onCanvasClick);

    window.addEventListener("resize", () => {
        recalcGeometry();
        draw(currentR, currentPoints);
    });
});

// ======================= GEOMETRY ========================
function recalcGeometry() {
    if (!canvasPlot) return;

    canvasPlotWidth = canvasPlot.clientWidth || canvasPlot.width;
    canvasPlotHeight = canvasPlot.clientHeight || canvasPlot.height;

    const maxScaleX = (canvasPlotWidth / 2 - padding) / MAX_R;
    const maxScaleY = (canvasPlotHeight / 2 - padding) / MAX_R;

    scaleX = scaleY = Math.max(Math.min(maxScaleX, maxScaleY), MIN_SCALE);

    xAxis = canvasPlotWidth / 2;
    yAxis = canvasPlotHeight / 2;

    ctx.textAlign = "left";
    ctx.textBaseline = "top";
}


function onCanvasClick(event) {
    const info = document.getElementById("validation-info");
    if (info) info.textContent = "";

    const rect = canvasPlot.getBoundingClientRect();
    const x = event.clientX - rect.left;
    const y = event.clientY - rect.top;

    const tableX = (x - xAxis) / scaleX;
    const tableY = (yAxis - y) / scaleY;

    if (tableX < -2 || tableX > 2) {
        if (info) info.textContent = "Значение X не соответствует диапазону [-2; 2]!";
        return;
    }

    if (tableY < -5 || tableY > 5) {
        if (info) info.textContent = "Значение Y не соответствует диапазону [-5; 5]!";
        return;
    }


    ctx.beginPath();
    ctx.arc(x, y, 4, 0, 2 * Math.PI);
    ctx.fillStyle = "#2b2d42";
    ctx.fill();
    ctx.closePath();

    if (typeof sendData === "function") {
        sendData(tableX.toFixed(2), tableY.toFixed(2));
    }
}


function formatNum(v) {
    const n = Number(v);
    return Number.isInteger(n) ? n : n.toFixed(2).replace(/\.00$/, "");
}


function draw(r, points) {
    if (!ctx || !canvasPlot) return;

    recalcGeometry();
    ctx.clearRect(0, 0, canvasPlotWidth, canvasPlotHeight);

    drawGrid();
    drawAxes();

    const parsedR = Number(r);
    currentR = (!r || isNaN(parsedR)) ? defaultR : parsedR;

    drawText(currentR);
    drawPolygon(currentR);

    if (points === undefined) {
        points = currentPoints;
    } else {
        currentPoints = points || [];
    }

    if (Array.isArray(points)) {
        points.forEach(point => {
            const hit = isHitClient(point.x, point.y, currentR);
            const color = hit ? "green" : "red";
            drawPoint(point.x, point.y, color);
        });
    }
}


function isHitClient(x, y, r) {
    x = Number(x);
    y = Number(y);

    // 1. Прямоугольник (II четверть)
    if (x <= 0 && x >= -r && y >= 0 && y <= r) return true;

    // 2. Треугольник (III четверть)
    // y >= -(x + r/2)
    if (x <= 0 && y <= 0 && y >= -x - r/2) return true;

    // 3. Четверть круга (IV четверть)
    if (x >= 0 && y <= 0 && (x*x + y*y <= r*r)) return true;

    return false;
}


function drawGrid() {
    ctx.beginPath();
    ctx.strokeStyle = "#ced0ce";

    for (let x = 0; x <= canvasPlotWidth; x += scaleX) {
        ctx.moveTo(x, 0);
        ctx.lineTo(x, canvasPlotHeight);
    }
    for (let y = 0; y <= canvasPlotHeight; y += scaleY) {
        ctx.moveTo(0, y);
        ctx.lineTo(canvasPlotWidth, y);
    }

    ctx.stroke();
    ctx.closePath();
}


function drawAxes() {
    ctx.font = `${Math.round(scaleX / 2)}px Arial`;
    ctx.fillStyle = "black";

    ctx.beginPath();
    ctx.strokeStyle = "#000000";

    ctx.moveTo(xAxis, 0);
    ctx.lineTo(xAxis, canvasPlotHeight);
    ctx.fillText("y", xAxis - shiftAxisNames, 0);

    ctx.moveTo(0, yAxis);
    ctx.lineTo(canvasPlotWidth, yAxis);
    ctx.fillText("x", canvasPlotWidth - shiftAxisNames, yAxis - shiftAxisNames);

    ctx.stroke();
    ctx.closePath();
}


function drawText(r) {
    ctx.fillStyle = "#4f4f4f";
    ctx.font = `${Math.round(scaleX / 2)}px Arial`;

    const marksX = [-r, -r/2, 0, r/2, r];
    const marksY = [-r, -r/2, r/2, r];

    // X AXIS
    marksX.forEach(v => {
        const px = xAxis + v * scaleX;
        ctx.beginPath();
        ctx.moveTo(px, yAxis - 3);
        ctx.lineTo(px, yAxis + 3);
        ctx.stroke();
        ctx.closePath();

        ctx.fillText(formatNum(v), px + shiftNames, yAxis + shiftNames);
    });

    // Y AXIS
    marksY.forEach(v => {
        const py = yAxis - v * scaleY;
        ctx.beginPath();
        ctx.moveTo(xAxis - 3, py);
        ctx.lineTo(xAxis + 3, py);
        ctx.stroke();
        ctx.closePath();

        ctx.fillText(formatNum(v), xAxis + shiftNames, py + shiftNames);
    });
}


function drawPolygon(r) {
    drawRect(r);
    drawTriangle(r);
    drawArc(r);
}

function drawRect(r) {
    const w = scaleX * r;
    const h = scaleY * r;

    ctx.beginPath();
    ctx.rect(xAxis - w, yAxis - h, w, h);
    ctx.closePath();

    ctx.strokeStyle = "#8C3F5F";
    ctx.fillStyle = "rgba(140,63,95,0.35)";
    ctx.fill();
    ctx.stroke();
}

function drawTriangle(r) {
    const dx = scaleX * (r / 2);
    const dy = scaleY * (r / 2);

    ctx.beginPath();
    ctx.moveTo(xAxis, yAxis);
    ctx.lineTo(xAxis - dx, yAxis);
    ctx.lineTo(xAxis, yAxis + dy);
    ctx.closePath();

    ctx.strokeStyle = "#8C3F5F";
    ctx.fillStyle = "rgba(140,63,95,0.35)";
    ctx.fill();
    ctx.stroke();
}

function drawArc(r) {
    const Rpx = scaleX * r;

    ctx.beginPath();
    ctx.moveTo(xAxis, yAxis);
    ctx.arc(xAxis, yAxis, Rpx, 0, Math.PI/2, false);
    ctx.closePath();

    ctx.strokeStyle = "#8C3F5F";
    ctx.fillStyle = "rgba(140,63,95,0.35)";
    ctx.fill();
    ctx.stroke();
}


function drawPoint(x, y, color) {
    x = Number(x);
    y = Number(y);

    const px = xAxis + x * scaleX;
    const py = yAxis - y * scaleY;

    ctx.beginPath();
    ctx.arc(px, py, 4, 0, 2*Math.PI);
    ctx.fillStyle = color;
    ctx.fill();
    ctx.closePath();
}
