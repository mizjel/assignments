//Source of truth: data received from the database, can be trusted to contain the correct results
//instead of getting the data from HTML elements
let attractions;
let unsavedOrders = [];

async function createAttractions(){
    let attractionsJson;
    try{
        let response = await fetch("api/attractions");
        attractionsJson = await response.json();
    } catch(ex){
        console.error(ex);
    }
    attractions = Object.assign(attractionsJson);

    createUnsavedOrders();

    createAttractionElements();
}
function clearAttractionElements(){
    let main = document.querySelector("main");

    let articles = main.querySelectorAll("article");
    articles.forEach(function(article, index){
        if(article.parentElement.tagName !== "ARTICLE") main.removeChild(article);
    });
}
function createAttractionElements(){
    clearAttractionElements();
    if(attractions !== null && attractions !== undefined) {
        attractions.forEach(createAttractionElement);
    }
    registerButtons();
    registerInputs();
}
function createUnsavedOrders(){
    if(attractions !== null && attractions !== undefined){
        attractions.forEach(createUnsavedOrder);
    }
}
function createUnsavedOrder(attraction, index){
    // Create order element with default values
    let unsavedOrder = { 'parkName' : attraction.name, 'numberOfAdults' : 0, 'numberOfKids' : 0, 'totalPrice' : 0 };
    unsavedOrders.push(unsavedOrder);
}
function createAttractionElement(attraction, index){    
    let unsavedOrder = unsavedOrders.find(o => o.parkName === attraction.name);
    
    let template = document.querySelector("template");
    let clone = template.content.cloneNode(true);

    let article = clone.querySelector("article");

    let divs = article.querySelectorAll("div");
    divs[0].innerText = attraction.available;
    divs[1].innerText = attraction.name;
    divs[2].innerText = attraction.description;

    let numberOfAdults = divs[3].querySelector(".numberofadults");
    numberOfAdults.value = unsavedOrder.numberOfAdults;

    let numberOfKids = divs[3].querySelector(".numberofkids");
    numberOfKids.value = unsavedOrder.numberOfKids;

    let prices = divs[3].querySelector(".prices");
    let adultPrice = prices.querySelector(".adultprice").querySelector(".price");
    adultPrice.innerText = attraction.adultPrice;

    let kidsPrice = prices.querySelector(".kidsprice").querySelector(".price");
    kidsPrice.innerText = attraction.kidsPrice;

    let totalPrice = divs[3].querySelector(".total").querySelector(".price")
    totalPrice.innerText = unsavedOrder.totalPrice.toLocaleString("nl-NL");

    let discountrequirement = prices.querySelector(".discountrequirement");
    
    let adultsRequirement = discountrequirement.querySelector(".adults");
    adultsRequirement.innerText = attraction.minimumNumberOfAdults;

    let kidsRequirement = discountrequirement.querySelector(".child");
    kidsRequirement.innerText = attraction.minimumNumberOfKids;
    
    let discountPercentage = discountrequirement.querySelector(".percentage");
    discountPercentage.innerText = attraction.discount;

    let orderButton = divs[3].querySelector(".orderbutton");
    orderButton.disabled = ((attraction.available === 0 || attraction.available === null) ? true : false);

    let main = document.querySelector("main");
    main.insertBefore(clone, template);
}
function saveOrderInShoppingBasket(order){
    let orders = JSON.parse(localStorage.getItem("orders"));
    if(orders === null) orders = [];

    orders.push(order);

    localStorage.setItem("orders", JSON.stringify(orders));

    //Update the shopping basket badge with the amount of orders saved
    //Badge is a class but there's only 1 badge so just get the first item
    let badge = document.querySelector(".badge");
    badge.innerText = orders.length;
}
function orderButtonClicked(event){
    let order = event.parentElement;
    let article = order.parentElement;
    let parkName = getParkNameFromArticle(article);
    
    let unsavedOrder = unsavedOrders.find(o => o.parkName === parkName);
    //uncomment below if statement if kids need to be accompanied by an adult so it only saves if at least the number of adults has been set or is not 0
    //if(unsavedOrder.numberOfAdults !== null && unsavedOrder.numberOfAdults !== undefined && unsavedOrder.numberOfAdults !== "" && unsavedOrder.numberOfAdults !== 0)
        saveOrderInShoppingBasket(unsavedOrder);
    unsavedOrders[unsavedOrders.indexOf(unsavedOrder)] = { 'parkName' : parkName, 'numberOfAdults' : 0, 'numberOfKids' : 0, 'totalPrice' : 0 };
    createAttractionElements();
}
function inputChanged(event){
    //Only get the numberofadults and numberofkids values from the HTML, rest can be done by querying the attractions global variable
    let order = event.parentElement;
    let article = order.parentElement;
    let parkName = getParkNameFromArticle(article);
    let attraction = attractions.find(p => p.name == parkName);
    let unsavedOrder = unsavedOrders.find(o => o.parkName === parkName);

    unsavedOrder.numberOfAdults = parseInt(order.querySelector(".numberofadults").value);
    unsavedOrder.numberOfKids = parseInt(order.querySelector(".numberofkids").value);

    // let availableTickets = parseInt(attraction.available);
    // if((numberOfAdults + numberOfKids) >= availableTickets){
    //     order.querySelector(".numberofadults").max = numberOfAdults;
    //     order.querySelector(".numberofkids").max = numberOfKids;
    // } else {
    //     order.querySelector(".numberofadults").max = availableTickets;
    //     order.querySelector(".numberofkids").max = availableTickets;
    // }

    let adultPrice = attraction.adultPrice;
    let kidsPrice = attraction.kidsPrice;
    //When ready: calculate total price and discount with a call to the backend/api so user cannot modify it
    let totalPriceNoDiscount = (unsavedOrder.numberOfAdults * adultPrice) + (unsavedOrder.numberOfKids * kidsPrice);

    let minimumNumberOfAdults = attraction.minimumNumberOfAdults;
    let minimumNumberOfKids = attraction.minimumNumberOfKids;
    let discountPercentage = attraction.discount;

    let discountPrice = 0;
    if(unsavedOrder.numberOfAdults >= minimumNumberOfAdults && unsavedOrder.numberOfKids >= minimumNumberOfKids) 
        discountPrice = (totalPriceNoDiscount * discountPercentage) / 100;
    
    unsavedOrder.totalPrice = (totalPriceNoDiscount - discountPrice);
    createAttractionElements();
}
function registerButtons(){
    let buttons = document.querySelectorAll(".orderbutton");

    buttons.forEach(
        btn => btn.addEventListener("click", function() { orderButtonClicked(this) })
    );
}
function registerInputs(){
    let inputs = document.querySelectorAll(".numberofadults, .numberofkids")
    
    inputs.forEach(
        inpt => inpt.addEventListener("click", function(){ inputChanged(this) })
    );
}
function setBadgeAmount(){
    let badge = document.querySelector(".badge");
    //Get items from local storage, set badge innertext to array length
    let orders = readOrdersFromLocalStorage();
    if(orders !== null && orders !== undefined){
        badge.innerText = orders.length;
    }
}
function readOrdersFromLocalStorage(){
    ordersString = JSON.parse(localStorage.getItem("orders"));
    if(ordersString !== null && ordersString !== undefined) return Object.assign(ordersString);
}
function getNumberOfPeopleFromClass(orderButton, className){
    let element = orderButton;
    while(!element.classList.contains(className)){
        element = element.previousElementSibling;
    }
    return element.value;
}
function getParkNameFromArticle(article){
    let element = article.firstElementChild;
    while(!element.classList.contains("parkname")){
        element = element.nextElementSibling;
    }
    return element.textContent;
}