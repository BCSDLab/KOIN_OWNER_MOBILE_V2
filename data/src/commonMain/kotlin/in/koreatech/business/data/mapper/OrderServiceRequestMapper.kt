package `in`.koreatech.business.data.mapper

import `in`.koreatech.business.data.request.order.OrderServiceMenuRequest
import `in`.koreatech.business.data.request.order.OrderServiceRequest
import `in`.koreatech.business.domain.model.order.OrderServiceApplication

internal fun OrderServiceApplication.toOrderServiceRequest(): OrderServiceRequest = OrderServiceRequest(
    minimumOrderAmount = minimumOrderAmount,
    isTakeout = isTakeout,
    deliveryOption = deliveryOption,
    campusDeliveryTip = campusDeliveryTip,
    offCampusDeliveryTip = offCampusDeliveryTip,
    businessLicenseUrl = businessLicenseUrl,
    businessCertificateUrl = businessCertificateUrl,
    bankCopyUrl = bankCopyUrl,
    bank = bank,
    accountNumber = accountNumber,
    accountHolder = accountHolder,
    menus = menuIds.map { OrderServiceMenuRequest(menuId = it) }
)
