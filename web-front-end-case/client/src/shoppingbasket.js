function setShoppingBasket(){
    let orders = readOrdersFromLocalStorage();

    let badge = document.querySelector(".badge");
    if(orders !== null && orders !== undefined) badge.innerText = orders.length;

    createOrderElements(orders);

    let paymentButton = document.querySelector("#finalizepaymentbutton");
    paymentButton.addEventListener("click", function(){ finalizePayment(orders); });
}
async function finalizePayment(orders){
    if(orders !== null && orders !== undefined){
        try{
            let response = await postData(orders);
            if(response.status === 200){
                localStorage.clear();
                
                window.location.href = "orderplaced.html";
            }
        } catch(ex){
            console.error(ex);
        }
    }
}

function readOrdersFromLocalStorage(){
    ordersString = JSON.parse(localStorage.getItem("orders"));
    if(ordersString !== null && ordersString !== undefined) return Object.assign(ordersString);
}
function createOrderElements(orders){
    if(orders !== null && orders !== undefined) {
        orders.forEach(createOrderElement);
    }
    
}
function createOrderElement(order, index){
    let template = document.querySelector("#ticket");
    let clone = template.content.cloneNode(true);
    
    let article = clone.querySelector("article");
    let divs = article.querySelectorAll("div");
    divs[0].innerText = order.parkName;
    divs[1].innerText += order.numberOfAdults;
    divs[2].innerText += order.numberOfKids;
    divs[3].innerText += order.displayPrice;

    let main = document.querySelector("main");
    let paymentButton = document.querySelector("#finalizepaymentbutton");

    main.insertBefore(clone, paymentButton);
}
async function postData(data = {}) {
    let url = "api/placeorder";

    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    });
    return response;
  }