package `in`.koreatech.business.data.mapper

import `in`.koreatech.business.data.response.order.OwnerOrderDetailResponse
import `in`.koreatech.business.data.response.order.OwnerOrderMenuOptionResponse
import `in`.koreatech.business.data.response.order.OwnerOrderMenuResponse
import `in`.koreatech.business.data.response.order.OwnerOrderPaymentResponse
import `in`.koreatech.business.data.response.order.OwnerOrderReceiverResponse
import `in`.koreatech.business.data.response.order.OwnerOrderResponse
import `in`.koreatech.business.data.response.order.OwnerOrderableShopResponse
import `in`.koreatech.business.domain.model.order.OwnerOrder
import `in`.koreatech.business.domain.model.order.OwnerOrderDetail
import `in`.koreatech.business.domain.model.order.OwnerOrderMenu
import `in`.koreatech.business.domain.model.order.OwnerOrderMenuOption
import `in`.koreatech.business.domain.model.order.OwnerOrderPayment
import `in`.koreatech.business.domain.model.order.OwnerOrderReceiver
import `in`.koreatech.business.domain.model.order.OwnerOrderableShop

internal fun OwnerOrderableShopResponse.toDomain() = OwnerOrderableShop(orderableShopId, shopId, name, address, isOpen)

internal fun OwnerOrderResponse.toDomain() = OwnerOrder(id, orderNumber, orderType, orderStatus, orderedAt, estimatedAt, totalPrice)

internal fun OwnerOrderDetailResponse.toDomain() = OwnerOrderDetail(
    id, orderNumber, orderType, orderStatus, orderedAt, orderMenus.map { it.toDomain() },
    receiver.toDomain(), payment.toDomain(), completedAt, canceledAt, canceledReason
)

private fun OwnerOrderMenuResponse.toDomain() = OwnerOrderMenu(
    id,
    menuName,
    menuPriceName,
    menuPrice,
    quantity,
    options.map { it.toDomain() }
)

private fun OwnerOrderMenuOptionResponse.toDomain() =
    OwnerOrderMenuOption(optionGroupName, optionName, optionPrice, quantity)

private fun OwnerOrderReceiverResponse.toDomain() =
    OwnerOrderReceiver(name, phoneNumber, address, addressDetail, toOwner, toRider, provideCutlery)

private fun OwnerOrderPaymentResponse.toDomain() =
    OwnerOrderPayment(method, approvedAt, totalProductPrice, deliveryTip, discountAmount, totalPrice)
