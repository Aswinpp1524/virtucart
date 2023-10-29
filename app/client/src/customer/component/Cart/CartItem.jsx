import { Button, IconButton } from "@mui/material";
import React from "react";
import RemoveCircleOutlineIcon from '@mui/icons-material/RemoveCircleOutline';
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';

const CartItem = () => {
  return (
    <div className="p-5 shadow-lg border rounded-md text-left">
      <div className="flex items-center">
        <div className="w-[5rem] h-[5rem] lg:w-[9rem] lg:h-[9rem]">
          <img
            className="w-full h-full object-cover object-top"
            src="https://rukminim2.flixcart.com/image/224/224/xif0q/kurta/e/s/9/l-nairawine-marisy-original-imagzzt4seyeh3z5.jpeg?q=90"
            alt=""
          />
        </div>

        <div className="ml-5 space-y-1">
          <p className="font-semibold">MARISY Women Printed A-line Kurta</p>
          <p className="opacity-70"> Size: L, Purple</p>
          <p className="opacity-70 mt-2">Seller: Prakash Cloth</p>
          <div className="flex space-x-5 items-center text-gray-900 pt-6">
            <p className="font-semibold">₹199</p>
            <p className="opacity-50 line-through">₹211</p>
            <p className="text-green-600 font-semibold">5% Off</p>
          </div>
        </div>

       
      </div>
      <div className="lg:flex items-center lg:space-x-10 pt-4">
            <div className="flex items-center space-x-2">
                <IconButton>
                    <RemoveCircleOutlineIcon/>
                </IconButton>
                <span className="py-1 px-7 border rounded-sm">3</span>
                <IconButton sx={{color:"RGB(145 85 253)"}}>
                    < AddCircleOutlineIcon/>
                </IconButton>
               
               
            </div>
            <div>
                <Button sx={{color:"RGB(145 85 253)"}}>
                    remove
                </Button>
            </div>
        </div>
    </div>
  );
};

export default CartItem;
